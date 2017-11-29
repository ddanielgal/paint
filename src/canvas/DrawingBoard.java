package canvas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import tools.*;

import javax.swing.JPanel;


public class DrawingBoard extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static final int IMAGE_WIDTH = 700;
	private static final int IMAGE_HEIGHT = 350;
	
	private BufferedImage img;
	private Map<String, DrawTool> tools = new HashMap<String, DrawTool>();
	private DrawTool selectedTool;

	/**
	 * Class constructor
	 * <p>
	 * Creates a new white drawing board with the default image size values. 
	 * Calls initTools() and selects the Brush tool. 
	 */
	public DrawingBoard(){
		img = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, img.getWidth(), img.getHeight());
		
		this.addMouseListener(new clickListener());
		this.addMouseMotionListener(new motionListener());
		
		initTools();
		selectTool("Brush");
	}
	
	
	/**
	 * Initializes tools
	 * <p>
	 * Add tools to the drawing board in this function.
	 */
	public void initTools(){
		tools.put("Brush", new Brush(getImageGraphics()));
		tools.put("Pencil", new Pencil(getImageGraphics()));
	}
	
	
	/**
	 * Get the names of the currently used tools in an array.
	 * @return an array of Strings with the tool names
	 */
	public String[] getToolNameArray(){
		return tools.keySet().toArray(new String[tools.size()]);
	}
	
	
	/**
	 * Select a tool from the currently used tools.
	 * @param t the name of the tool we want to select
	 */
	public void selectTool(String t){
		selectedTool = tools.get(t);
	}
	
	
	/**
	 * Get the currently selected tool.
	 * @return the tool that is currently selected
	 */
	public PaintingDrawTool getSelectedTool(){
		return (PaintingDrawTool) selectedTool;
	}
	
	
	/**
	 * Get the graphics of the BufferedImage.
	 * @return the graphics of the image
	 */
	public Graphics getImageGraphics(){
		return img.getGraphics();
	}
	
	
	/**
	 * Get the BufferedImage component of the DrawingBoard.
	 * @return the image
	 */
	public BufferedImage getImage(){
		return img;
	}
	
	
	/**
	 * Image setter method.
	 * <p>
	 * Sets the DrawingBoard's image to display.
	 * @param i the BufferedImage we want to set
	 */
	public void setImage(BufferedImage i){
		img = i;
		repaint();
		initTools();
		selectTool("Brush");
	}
	
	
	/**
	 * Override of the JPanel's paintComponent() method, draws the BufferedImage onto the JPanel.
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(img, 0, 0, null);
	}
	
	
	/**
	 * Override of the JPanel's getPreferredSize() method.
	 * <p>
	 * With this override, the JScrollPane will get the size of the BufferedImage and apply the scrollbars
	 * accordingly.
	 */
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(img.getWidth(), img.getHeight());
	}
	
	
	/**
	 * Mouse motion listener class.
	 * <p>
	 * Passes the mouse event to the currently selected tool's performAction() method.
	 * @author Dániel Gál
	 *
	 */
	class motionListener implements MouseMotionListener {

		public void mouseDragged(MouseEvent drag) {
			selectedTool.performAction(drag);
			repaint();
		}

		public void mouseMoved(MouseEvent move) {}
	}
	

	/**
	 * Mouse click listener class.
	 * <p>
	 * Passes the mouse event to the currently selected tool's performAction() method.
	 * @author Dániel Gál
	 *
	 */
	class clickListener implements MouseListener {

		public void mouseClicked(MouseEvent click) {
			selectedTool.performAction(click);
			repaint();
		}

		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
	}

}