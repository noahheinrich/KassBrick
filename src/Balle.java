import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Balle {

	double x,y;
	int hauteur,largeur;
	private double dx = 4;
	private double dy = -4;
	Color coulBalle;
	
	Image ballImg;
	
	String imgBallAdress="images/grande_balle.png";
	
	Rectangle monRectBall;
	
	public Balle(double _x, double _y, int _hauteur, int _largeur) {

		this.x = _x;
		this.y = _y;
		this.hauteur = _hauteur;
		this.largeur = _largeur;
		coulBalle = Color.black;
		
		try {
			ballImg=Toolkit.getDefaultToolkit().getImage((imgBallAdress));
		}catch (Exception e) {
			System.out.println("erreur dans le chargement des images:"+e);
		}
		System.out.println("chargement des images ok");
		
		this.monRectBall=new Rectangle((int)x,(int)y,largeur,hauteur);
	}
	
	public void Dessiner(Graphics g) {
		
		g.drawImage(ballImg,(int)x,(int)y,largeur,hauteur, null);
	}
	
	public void bouge() {
		if(x+dx-13<0 || x+dx>KassMMi.W-14-this.largeur)
			dx=-dx;
		
		if(y+dy-AireDeJeu.epaisseur-this.hauteur-7<0 || y+dy>KassMMi.H)
			dy=-dy;
		
		x+=dx;
		y+=dy;
		
	}
	
	void setXDir(double x) {
        if (Math.abs(x) < 20) {
        	dx = x;
        } else if (x > 20) {
        	dx = 20;
        } else if (x < -20) {
        	dx = -20;
        }
    }

    void setYDir(double y) {
    	if (Math.abs(y) < 20) {
        	dy = y;
        } else if (y > 20) {
        	dy = 20;
        } else if (y < -20) {
        	dy = -20;
        }
    }

    double getYDir() {

        return dy;
    }
    
    double getXDir() {
    	
    	return dx;
    }
	
	public Rectangle getRect() {
		Rectangle R = new Rectangle((int)this.x, (int)this.y, this.largeur, this.hauteur);
		return R;
	}
	
	public void setX(double x) {
		this.x = x;		
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public int getDiametre() {
		return this.largeur;
	}
}
