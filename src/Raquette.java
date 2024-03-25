import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Raquette {

	private int x,y;
	int dx;
	int largeur = 60;
	int hauteur = 10;
	Color coulRaquette;
	
	Image raqImg;
	
	String imgRaqAdress="images/moyenne_plateforme.png";
	
	Rectangle monRectRaq;
	
	public Raquette(int x, int y) {
		
		this.x = x;
		this.y = y;
		coulRaquette = Color.blue;
		
		try {
			raqImg=Toolkit.getDefaultToolkit().getImage((imgRaqAdress));
		}catch (Exception e) {
			System.out.println("erreur dans le chargement des images:"+e);
		}
		System.out.println("chargement des images ok");
	
		this.monRectRaq=new Rectangle(x,y,largeur,hauteur);
	}
	
	public void Dessiner(Graphics g) {
		
		this.monRectRaq=new Rectangle(x,y,largeur,hauteur);
		g.drawImage(raqImg,x,y,largeur,hauteur, null);
	}
	
	public void Maj(int newX) {
		dx = (int)(newX-x)/3;
		x=newX;
	}
	
	public Rectangle getRect() {
		Rectangle R = new Rectangle(this.x, this.y, this.largeur, this.hauteur);
		return R;
	}
	
	public void setX(int x) {
		if(x>0+13 && x<KassMMi.W-14-this.largeur)
			this.x=x;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getHauteur() {
		return this.hauteur;
	}
	
	public void bouge(int x, int y) {
		if(x>0 && x<KassMMi.W-this.largeur)
			this.x+=x;
	}
	
	public int largeur() {
		return this.largeur;
	}
}
