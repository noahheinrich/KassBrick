import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

public class KassMMi extends JFrame implements Runnable,KeyListener,MouseListener,MouseMotionListener{
	
	private Image DoubleBuffer;
	private Graphics gBuffer;
	
	Thread processusJeu;
	
	AireDeJeu monADJ;
	static int W = 500;
	static int H = 700;
	
	static int vies = 3;
	
	private boolean jeuEnCours = false;
	
	public KassMMi(){
		
		super("KassMMI 23");
		this.setSize(500,700);
		
		monADJ = new AireDeJeu(0,0,500,1000);
		
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		DoubleBuffer = createImage(W,H);
		gBuffer = DoubleBuffer.getGraphics();
				
		processusJeu=new Thread(this);
		
		processusJeu.start();
		
		
		
		
	}
	
	public void paint(Graphics g) {
		
		super.paint(gBuffer);
		
		gBuffer.setColor(Color.white);
		gBuffer.fillRect(0, 0, W, H);
		
		if (!jeuEnCours) {
			this.monADJ.balle.setX((this.monADJ.raquette.getX() + this.monADJ.raquette.largeur() / 2)-5);
            this.monADJ.balle.setY(this.monADJ.raquette.getY() - this.monADJ.balle.getDiametre());
		}
		
		if (monADJ.balle.getY() > monADJ.raquette.getY() + monADJ.raquette.getHauteur() + 20) {
			vies -= 1;
			if (vies == 0) {
				monADJ.gameEnded = true;
				monADJ.gameLost = true;
			}
			
			System.out.println(vies);
		
			jeuEnCours = false;
		}
		
		
		
		this.monADJ.Dessiner(gBuffer);
		
		g.drawImage(DoubleBuffer, 0, 0, this);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		KassMMi monJeu=new KassMMi();

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_DOWN :
			System.out.println("bas");
			this.repaint();
			break;
		
		case KeyEvent.VK_RIGHT :
			System.out.println("droit");
			this.monADJ.raquette.bouge(5,0);
			
			this.repaint();
			break;
		
		case KeyEvent.VK_UP :
			System.out.println("haut");
			this.repaint();
			break;
			
		case KeyEvent.VK_LEFT :
			System.out.println("gauche");
			this.monADJ.raquette.bouge(-5,0);
			
			this.repaint();
			break;
		
		case KeyEvent.VK_SPACE :
			if (jeuEnCours) break;
			System.out.println("espace");
			jeuEnCours = true;
			
			this.repaint();
			break;
		
		}
	}

	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
		monADJ.raquette.Maj(e.getX());
		
		//System.out.println(xSouris+"/"+ySouris);
		
		this.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				if(jeuEnCours && !monADJ.gameEnded) {
					
					this.monADJ.balle.bouge();
					this.monADJ.testcollision();
					
				}		
				processusJeu.sleep(20);
			}
			catch (Exception ex) {
			}
			repaint();
		}
			
	}

}
