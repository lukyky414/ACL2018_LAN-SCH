package model.entity;

import engine.Cmd;
import model.plateau.Effect;
import model.plateau.SecretPassage;
import model.plateau.Square;
import model.plateau.Wall;

import java.util.ArrayList;
import java.util.Random;

public abstract class Monster extends Movable {

	static private Random rdm = new Random();
	private Hero target;

	private int difficulty;

	/**
	 * Permet de creer un tableau d'action,
	 * facilite la selection d'une difficulte d'ia.
	 */
	interface iaAction {
		Cmd ia();
	}
	private iaAction[] iaActions;

	public Monster(Square position, int hp, int atk, int cooldown, Hero target, int difficulty) {
		super(position,hp, atk, cooldown);
		this.target = target;

		if(difficulty < 0 || difficulty > 3)
			throw new IllegalArgumentException("La difficulte choisie n'existe pas.");
		this.difficulty = difficulty;

		/*
		 * Cree un tableau de methode.
		 * en gros: iaActions = new iaAction[]{
		 * 	new iaAction() { @Override Cmd ia(){ return Cmd.IDLA;}},
		 * 	new iaAction() { @Override Cmd ia(){ return iaEasy();}},
		 * 	new iaAction() { @Override Cmd ia(){ return iaMedium();}},
		 * 	new iaAction() { @Override Cmd ia(){ return iaHard();}}
		 * }
		 */
		iaActions = new iaAction[] {
				() -> Cmd.IDLE,
				this::iaEasy,
				this::iaMedium,
				this::iaHard
		};
	}


	/**
	 * Il faut aussi diminuer la vitesse de deplacement.
	 */
	@Override
	public void move(){
		if(cooldown-- == 0){
			super.move();
		}
	}

	/**
	 * Permet d'activer l'IA.
	 * On ne lance pas l'IA a chaque frame, mais avant chaque deplacement.
	 *  difficulty 0 -> ne bouge pas
	 */
	@Override
	public void evolve(){
		if(cooldown == 0) {
			this.nextPos = getNextPos(this.getPos(), this.iaActions[difficulty].ia());
		}
	}

	public void attack(){
		if(this.canAttack()) {
			ArrayList<Entity> entitiesAround = this.getPos().lookAround();
			if(!entitiesAround.isEmpty()){
				for(Entity e : entitiesAround){
					if(e == target) {
						if (!(e.isDead()))
							e.diminuerHp(this.attack);
					}
				}
			}
			resetCooldown();
		}
	}

	/**
	 * Deplacement aleatoire pour une ia facile.
	 *
	 * @return Cmd - la direction choisie
	 */
	protected Cmd iaEasy(){
		switch(rdm.nextInt(9)){
			case 0:
			case 1:
				return Cmd.DOWN;
			case 2:
			case 3:
				return Cmd.RIGHT;
			case 4:
			case 5:
				return Cmd.LEFT;
			case 6:
			case 7:
				return Cmd.UP;
			default:
				return Cmd.IDLE;
		}
	}

	/**
	 * Se rapprocher en X ou en Y.
	 * Est peut etre trop puissante pour le fantome, qui devrait se rapprocher de la distance la plus courte d'abord
	 *
	 * @return Cmd - la direction choisie
	 */
	protected Cmd iaMedium(){
		int distX = target.getPos().getPosX() - this.getPos().getPosX(); //Positif = monstre a gauche // Negatif = monstre a droite
		int distY = target.getPos().getPosY() - this.getPos().getPosY(); //Positif = monstre en haut // Negatif = monstre en bas

		Cmd dirH = Cmd.IDLE;
		Cmd dirV = Cmd.IDLE;

		//Pour un rapprochement Horizontal
		if(distX > 0)
			dirH = Cmd.RIGHT;
		if(distX < 0)
			dirH = Cmd.LEFT;

		//Pour un rapprochement Vertical
		if(distY > 0)
			dirV = Cmd.DOWN;
		if(distY < 0)
			dirV = Cmd.UP;

		if(Math.abs(distX) < Math.abs(distY)){
			//Distance verticale plus grande -> priorite au rapprochement Vertical.

			if(dirV == Cmd.IDLE || !canMove(getNextPos(this.getPos(), dirV))){
				//Si on est sur le meme axe horizontal (distance Y = 0) (target Y = Monster Y) -> dirV = IDLE
				//Si on ne peut se rapprocher Verticalement (mur) (bord de map) (entite bloquante) -> canMove = false
				//Alors: on se rapproche Horizontalement.
				return dirH;

				//Pas de test a faire: si on est sur le meme axe horizontal -> impossible (target.pos = monster.pos) ou alors target = monster.
				//si le deplacement est impossible (par ex: coince dans un coin et le hero de l'autre cote: on ne bouge pas (ia pas intelligente))
			}

			//Aucun probleme, on se rapproche
			return dirV;
		}
		else{
			//Distance horizontale plus grande -> priorite au rapporchement Horizontal.

			if(dirH == Cmd.IDLE || !canMove(getNextPos(this.getPos(), dirH))){
				//Si on est sur le meme axe vertical (distance X = 0) (target X = Monster X) -> dirH = IDLE
				//Si on ne peut se rapprocher horizontalement (mur) (bord de map) (entite bloquante) -> canMove = false
				//Alors: on se rapproche Verticalement.
				return dirV;

				//Pas de test a faire: si on est sur le meme axe vertical -> impossible (target.pos = monster.pos) ou alors target = monster.
				//si le deplacement est impossible (par ex: coince dans un coin et le hero de l'autre cote: on ne bouge pas (ia pas intelligente))
			}

			//Aucun probleme, on se rapproche
			return dirH;
		}
	}

	/**
	 * Se rapprocher en X ou en Y.
	 * Maniere peu efficace, qui reduit d'abord la distance la plus courte.
	 *
	 * @return Cmd - la direction choisie
	 */
	protected Cmd iaHard(){
		Inondation in = new Inondation(this.getPos(), target.getPos());
		Cmd dir = in.getFastestDir();
		return dir;
	}

	public int getDifficulty(){
		return this.difficulty;
	}

	//#########################
	//#                       #
	//#  Pour l'IA difficile  #
	//#                       #
	//#########################

	class iFoundIt extends Exception {
		mySquare sq;

		public iFoundIt(mySquare sq){
			this.sq = sq;
		}

		public mySquare getSquare(){
			return sq;
		}
	}

	class Inondation{
		Square _startPos;
		Square _finisPos;
		ArrayList<Square> _visitedPos;
		ArrayList<mySquare> _toVisitPos;

		public Inondation(Square startPos, Square finishPos){
			this._startPos = startPos;
			this._finisPos = finishPos;
			this._visitedPos = new ArrayList<>();
			this._toVisitPos = new ArrayList<>();
			this._toVisitPos.add(new mySquare(startPos, null));
		}

		public Cmd getFastestDir(){
			try{
				while(_toVisitPos.size() > 0){
					oneIteration();
				}
			} catch (iFoundIt err) {
				mySquare tmp = err.getSquare().getFirstSon();
				return err.getSquare().getNextDir();
			}
			return Cmd.IDLE;
		}

		private void oneIteration() throws iFoundIt {
			int i, lenght = _toVisitPos.size();
			for(i = 0; i < lenght; i++){
				mySquare actual = _toVisitPos.get(i);

				if(actual.thisPos == _finisPos)
					throw new iFoundIt(actual);//*/


				if(!_visitedPos.contains(actual.thisPos)) {
					//Permet au monstre de passer dans un passage secret.
					for(Effect e : actual.thisPos){
						if(e instanceof SecretPassage){
							int x = ((SecretPassage) e).getPosXSortie();
							int y = ((SecretPassage) e).getPosYSortie();
							Square sq = actual.thisPos.getMap().getSquare(x, y);
							mySquare msq = new mySquare(sq , actual);
							if(sq != null && !_visitedPos.contains(sq) && !(sq instanceof Wall))
								_toVisitPos.add(msq);
						}
					}

					//Inondation
					ArrayList<Square> l = actual.thisPos.neighboor();
					for (Square s : l) {
						mySquare msq = new mySquare(s , actual);
						if (!_visitedPos.contains(s) && !(s instanceof Wall)) {
							_toVisitPos.add(msq);
						}
					}
					_visitedPos.add(actual.thisPos);
				}
			}

			for(i = 0; i < lenght; i++)
				_toVisitPos.remove(0);
		}
	}

	class mySquare{
		public mySquare father;
		public Square thisPos;

		public mySquare(Square pos, mySquare father){
			this.father = father;
			this.thisPos = pos;
		}

		public mySquare getFirstSon(){
			mySquare first = this;
			mySquare second = null;

			while(first.father != null){
				second = first;
				first = first.father;
			}

			return second;
		}

		public Cmd getNextDir(){
			mySquare next = getFirstSon();
			mySquare first = next.father;

			return getDirection(first.thisPos, next.thisPos);
		}
	}
}