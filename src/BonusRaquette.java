import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class BonusRaquette {
    Image imgBarrel;
    String imgBarrelAdress = "Images/bonus1.png";
    Rectangle rHori, rVert;
    double y, dy=0.5;
    int x, r=15;

    public BonusRaquette(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            imgBarrel=Toolkit.getDefaultToolkit().getImage((imgBarrelAdress));
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de l'image du tonneau");
        }
        System.out.println("Chargement des images ok");
    }

    public boolean colBonus(Rectangle Raq) {

        this.rHori = new Rectangle((int)x, (int)y+r, 2*r, 2);
        this.rVert = new Rectangle((int)x+r, (int)y, 2, 2*r);

        if(this.rHori.intersects(Raq) || this.rVert.intersects(Raq)) {
            return true;
        }

        return false;
    }

    public void update() {
        y += dy;
    }

    public void dessiner(Graphics g) {
        g.drawImage(imgBarrel, this.x, (int)this.y,2*r, 2*r, null);
    }
}