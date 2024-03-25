import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Brick {
	
	int x,y;
	int largeur=30;
	int hauteur = 10;
	Color coulBrick;
	int pdv;
	private boolean destroyed;
	
	Image brickImg;
	Image brickImg2;
	Image brickImg3;
	
	String imgBrickAdress="images/brique.png";
	String imgBrick2Adress="images/brique2.png";
	String imgBrick3Adress="images/brique3.png";
	
	Rectangle rCol;
	
	public Brick(int _x, int _y, int pdv) {
		
		coulBrick=Color.red;
		this.x = _x;
		this.y = _y;
		this.pdv = pdv;
		
		destroyed = false;
		
		try {
			brickImg=Toolkit.getDefaultToolkit().getImage(imgBrickAdress);
			brickImg2=Toolkit.getDefaultToolkit().getImage(imgBrick2Adress);
			brickImg3=Toolkit.getDefaultToolkit().getImage(imgBrick3Adress);
		}catch (Exception e) {
			System.out.println("erreur dans le chargement des images:"+e);
		}
		System.out.println("chargement des images ok");
		
		this.rCol=new Rectangle(x,y,largeur,hauteur);
	}
	
	public void Dessiner(Graphics g) {
		if(!destroyed) {
			 if(pdv==1) {
	            g.drawImage(brickImg, x, y, largeur, hauteur, null);
	        } else if(pdv==2) {
	            g.drawImage(brickImg2, x, y, largeur, hauteur, null);
	        } else if(pdv==3) {
	            g.drawImage(brickImg3, x, y, largeur, hauteur, null);
	        }
		}
	}
	
	boolean isDestroyed() {
		return destroyed;
	}
	
	void setDestroyed(boolean val) {
		destroyed = val;
	}
	
	public Rectangle getRect() {
		Rectangle R = new Rectangle(this.x, this.y, this.largeur, this.hauteur);
		return R;
	}
}
