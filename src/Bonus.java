import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Bonus {
	
	private int x,y,largeur,hauteur, dy;
	
	Image bonus1Img;
	
	String imgBonus1Adress="images/bonus1.png";
	
	Rectangle monRectBonus;
	
	public Bonus(int x, int y, int largeur, int hauteur, int dy) {
		this.x = x;
		this.y = y;
		this.largeur = largeur;
        this.hauteur = hauteur;
        this.dy = dy;
        
		try {
			bonus1Img=Toolkit.getDefaultToolkit().getImage((imgBonus1Adress));
		}catch (Exception e) {
			System.out.println("erreur dans le chargement des images:"+e);
		}
		System.out.println("chargement des images ok");
		
		this.monRectBonus=new Rectangle(x,y,largeur,hauteur);
	}
	
	public void Dessiner(Graphics g) {
		
		g.drawImage(bonus1Img,x,y,largeur,hauteur, null);
	}
	
	public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public void bouge() {
        y += dy;
    }
}
