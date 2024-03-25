import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.io.File;

public class AireDeJeu {

	int x,y;
	int largeur,hauteur;
	Color coulFond;
	Color coulBord;
	static int epaisseur=30;
	int score = 0;
	
	private List<Bonus> bonusList;
	int largeurBonus = 16;
	int hauteurBonus = 16;
	int vitesseBonus = 1;
	
	int nbBricks;
	int nbBricksDestroyed;
	
	boolean gameEnded = false;
	static boolean gameLost = false;
	
	Image bordImg;
	
	String imgBordAdress="images/bordure.png";
	
	int numero_niveau;
	int briques_restantes;
	String fichier;
	
	Brick [][] tabBrick;
	
	Raquette raquette;
	
	Balle balle;
	
	BonusRaquette bonusRaquette;
	
	public AireDeJeu(int _x, int _y, int _w, int _h) {
		
		coulFond=Color.gray;
		coulBord=new Color(100,100,240);
		this.x=_x;
		this.y=_y;
		this.largeur=_w;
		this.hauteur=_h;
		
		bonusList = new ArrayList<>();
		
		try {
			bordImg=Toolkit.getDefaultToolkit().getImage((imgBordAdress));
		}catch (Exception e) {
			System.out.println("erreur dans le chargement des images:"+e);
		}
		System.out.println("chargement des images ok");
		
		tabBrick = new Brick[13][15];
		
		raquette = new Raquette((int)(this.largeur/2),this.hauteur-350);
		// this.x + this.largeur/2 - this.epaisseur, this.y + this.hauteur - this.epaisseur
		
		balle = new Balle((double)(this.largeur/2),(double)(this.hauteur-368), 15, 15);
		
		numero_niveau = 1;
		levelLoad(numero_niveau, tabBrick);
		
	}
	
	public void Dessiner(Graphics g){
		
		g.setColor(this.coulFond);
		g.fillRect(this.x, this.y, this.largeur, this.hauteur);
		
		g.drawImage(bordImg,x,y+30,largeur,hauteur, null);
		
		for (int i=0; i<13; i++) {
			for (int j=0; j<15; j++) {
				if (tabBrick[i][j]!=null) 
					this.tabBrick[i][j].Dessiner(g);				
			}
		}
		
		raquette.Dessiner(g);
		if (!gameEnded) {
			balle.Dessiner(g);
			g.setColor(Color.red);
			Font myFont = new Font("Arial", Font.PLAIN, 30);
			g.setFont(myFont);
			g.drawString("vies : " + KassMMi.vies, 30, 85);
			g.drawString("score : " + score, 150, 85);
		} else {
			if (!gameLost) {
		        g.setColor(Color.green); 
		        g.setFont(new Font("Arial", Font.BOLD, 48));
		        String message = "Victoire !";
		        int messageWidth = g.getFontMetrics().stringWidth(message);
		        int messageHeight = g.getFontMetrics().getHeight();
		        int centerX = (this.largeur - messageWidth) / 2;
		        int centerY = (this.hauteur + messageHeight) / 2;
		        g.drawString(message, centerX, centerY);
		        g.setFont(new Font("Arial", Font.PLAIN, 32));
				g.drawString("score final : " + score, 75, centerY+50);
		        
			} else {
				g.setColor(Color.RED); 
		        g.setFont(new Font("Arial", Font.BOLD, 48));
		        String message = "Perdu !";
		        int messageWidth = g.getFontMetrics().stringWidth(message);
		        int messageHeight = g.getFontMetrics().getHeight();
		        int centerX = (this.largeur - messageWidth) / 2;
		        int centerY = (this.hauteur + messageHeight) / 2;
		        g.drawString(message, centerX, centerY);
		        g.setFont(new Font("Arial", Font.PLAIN, 32));
		        g.drawString("score final : " + score, 75, centerY+50);
			}
	    }
		
	}
	
	public boolean checkExist(int numero_niveau) {
		 String filePath = "lvl/Level"+numero_niveau+".txt"; 
		 File file = new File(filePath);
		
	    if (file.exists()) {
	        return true;
	    }
	    return false;
	}
	
	public void nextLevel() {
		if (nbBricksDestroyed == nbBricks) {
			nbBricksDestroyed = 0;
			nbBricks = 0;
			KassMMi.vies++;
			numero_niveau++;
			
			if(checkExist(numero_niveau)) {
				levelLoad(numero_niveau, tabBrick);
			} else {
				gameEnded = true;
				return;
			}
			score += numero_niveau * 1000;
			balle.x = (raquette.getX() + raquette.largeur() / 2)-5;	
			balle.y = raquette.getY() - balle.getDiametre();
		}
	}
	
	public void levelLoad(int _numNiveau, Brick[][] TBriques) {
		
		String ligne; // pour accélerer le chargement, on va lire ligne par ligne 
		int numNiveau = _numNiveau; 
		
		int i,j; // indices de ligne et de colonne pour remplir le tableau 
		
		fichier = new String("lvl/Level"+numNiveau+".txt"); // chargement du fichier Level....dat dans le dossier lvl
		
		try {
			// création et ouverture d'un flux de lecture 
			BufferedReader tamponFichier = new BufferedReader(new InputStreamReader(new FileInputStream(fichier)));
			
			// lecture d'une ligne
			ligne = tamponFichier.readLine();
			
			// passage des commentaires commençant par #
			while (ligne.startsWith("#") == true) {
				ligne = tamponFichier.readLine();
			}
			
			// lecture du nombre de briques du niveau 
			ligne = tamponFichier.readLine();
			// affichage dans la fenêtre system d'informations de debuggage
			System.out.println(ligne);
			
			//transformation de la chaîne de caractères en valeur entière
			briques_restantes = Integer.parseInt(ligne); //new Byte(ligne).intValue(); // permet de connaître le nombre de briques à détruire  
			
			// affichage dans la fenêtre system d'informations de debuggage 
			System.out.println("briques_restantes: "+briques_restantes);
			
			// passage des commentaires 
			ligne = tamponFichier.readLine();
			while (ligne.startsWith("#")== true) {
				ligne = tamponFichier.readLine();
			}
			
			// affichage dans la fenêtre system d'informations de debuggage 
			System.out.println("Chargement du niveau ..., please wait");
			
			// passage de la ligne de commentaire annonçant le niveau 
			ligne = tamponFichier.readLine();
			
			// traitement de la 1ère ligne avec un tokenizer 
			// le séparateur est le ';'
			i=0; j=0; // initialisation des indices du tableau 
			char typeBrique='Z';
			while (ligne.startsWith(";") == false) {
				StringTokenizer st = new StringTokenizer(ligne, ";");
				while (st.hasMoreTokens()) {
					String token_suivant = st.nextToken();
					// Remplissage du tableau TBriques
					if (i<13) typeBrique = (token_suivant).charAt(0);
						switch(typeBrique) {
						case 'O':
							TBriques[i][j]= null;
							break;
						case 'A':
							TBriques[i][j]= new Brick(50+this.x+i*30,50+this.y+this.epaisseur+15*j,1);
							nbBricks++;
							break;
						case 'B':
							TBriques[i][j] = new Brick(50+this.x+i*30,50+this.y+this.epaisseur+15*j,2);
							nbBricks++;
							break;
						case 'C':
							TBriques[i][j] = new Brick(50+this.x+i*30,50+this.y+this.epaisseur+15*j,3);
							nbBricks++;
							break;
						default:
							TBriques[i][j] = new Brick(50+this.x+i*30,50+this.y+this.epaisseur+15*j,-1);
							break;
						} // fin du switch
					i++; // passage à la colonne suivante
				} //fin while(hasMoreToken)
				
				ligne = tamponFichier.readLine();
				j++; // passage à la ligne suivante
				i=0; // remise à 0 du compteur de colonne 
			} // fin du while (non fin de niveau ";" pour commencer
			
			tamponFichier.close();
		} // fin du try 
		catch (IOException e) {
			System.out.println("Erreur de lecture du fichier de niveau."+e);
			System.exit(0);
		}
		// on a chargé le tableau en erreur 
		
	}
	
	public void creerBonus(int x, int y) {
        Bonus bonus = new Bonus(x, y, largeurBonus, hauteurBonus, vitesseBonus);
        bonusList.add(bonus);
    }
	
	
	public void testcollision() {
		Rectangle rBalle=this.balle.getRect();
		Rectangle rRaquette=this.raquette.getRect();
		
		//if(BonusRaquette.colBonus(rRaquette)) {
			//raquette.largeur = 100;
		//}
		
		if(rBalle.intersects(rRaquette)) {
			
			int paddleLPos = (int) raquette.getRect().getMinX();
            int ballLPos = (int) balle.getRect().getMinX();

            int first = paddleLPos;
            int second = paddleLPos + 15;
            int third = paddleLPos + 30;
            int fourth = paddleLPos + 45;
            
            double facteur = 2;

            if (ballLPos < first) {

            	balle.setXDir(-4+raquette.dx*0.5);
            	balle.setYDir(-4+raquette.dx*0.5);
            }

            if (ballLPos >= first && ballLPos < second) {

            	balle.setXDir(-4+raquette.dx*0.8);
            	balle.setYDir(-2 * balle.getYDir()+raquette.dx*0.8);
            }

            if (ballLPos >= second && ballLPos < third) {

            	balle.setXDir(facteur*(0+(Math.random()*(2-(-2))-2))+raquette.dx);
            	balle.setYDir(facteur*-4+raquette.dx);
            }

            if (ballLPos >= third && ballLPos < fourth) {

            	balle.setXDir(4+raquette.dx*0.8);
            	balle.setYDir(-2 * balle.getYDir()+raquette.dx*0.8);
            }

            if (ballLPos > fourth) {

            	balle.setXDir(4+raquette.dx*0.5);
            	balle.setYDir(-4+raquette.dx*0.5);
            }
		}
		
		for (int i=0; i<13; i++) {
			for (int j=0; j<15; j++) {
				if (tabBrick[i][j]!=null && tabBrick[i][j].pdv>0)
				{
					if(this.tabBrick[i][j].rCol.intersects(rBalle)) {
					
						int ballLeft = (int) balle.getRect().getMinX();
		                int ballHeight = (int) balle.getRect().getHeight();
		                int ballWidth = (int) balle.getRect().getWidth();
		                int ballTop = (int) balle.getRect().getMinY();
	
		                var pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
		                var pointLeft = new Point(ballLeft - 1, ballTop);
		                var pointTop = new Point(ballLeft, ballTop - 1);
		                var pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);
		                
						if (!tabBrick[i][j].isDestroyed()) {
	
		                    if (tabBrick[i][j].getRect().contains(pointRight)) {
	
		                        balle.setXDir(-4);
		                        
		                    } else if (tabBrick[i][j].getRect().contains(pointLeft)) {
	
		                        balle.setXDir(4);
		                    }
	
		                    if (tabBrick[i][j].getRect().contains(pointTop)) {
	
		                        balle.setYDir(4);
		                    } else if (tabBrick[i][j].getRect().contains(pointBottom)) {
	
		                        balle.setYDir(-4);
		                    }
		                    this.tabBrick[i][j].pdv-=1;
		                    }
						if (tabBrick[i][j].pdv == 0) {
							score+=100;
							nbBricksDestroyed += 1;
							//int random = (int) (Math.random() * 500);
							//if (random < 10) {
							//	bonusRaquette = new BonusRaquette(tabBrick[i][j].x, tabBrick[i][j].y);
							//}
	                    	//tabBrick[i][j].setDestroyed(true);
	                                       	
		                }
						
						if (nbBricksDestroyed == nbBricks) {
							
							nextLevel();
						}
					}
				}
			}
		}
	}

	private void endGame() {
		// TODO Auto-generated method stub
		gameEnded = true;
	}
}
