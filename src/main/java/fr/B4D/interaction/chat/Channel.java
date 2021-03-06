package fr.B4D.interaction.chat;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.B4D.bot.B4D;
import fr.B4D.bot.B4DException;
import fr.B4D.program.CancelProgramException;
import fr.B4D.program.StopProgramException;
import fr.B4D.utils.PointF;

/** La classe {@code Channel} repr�sente un canal de diffusion du chat.<br><br>
 * Un canal est d�fini par un nom, un pr�fix et un point d'activation.
 */
public class Channel implements Serializable{
	
	private static final long serialVersionUID = 4872542387501307482L;
	
	  /****************/
	 /** COLLECTION **/
	/****************/
	
	public final static Channel GENERAL = new Channel("General", "/s", new PointF(0.024, -0.266 + 0*0.0216));
	public final static Channel TEAM = new Channel("Team", "/t", new PointF(0.024, -0.266 + 1*0.0216));
	public final static Channel GUILD = new Channel("Guild", "/g", new PointF(0.024, -0.266 + 2*0.0216));
	public final static Channel ALLIES = new Channel("Allies", "/a", new PointF(0.024, -0.266 + 3*0.0216));
	public final static Channel GROUP = new Channel("Group", "/p", new PointF(0.024, -0.266 + 4*0.0216));
	public final static Channel BUSINESS = new Channel("Business", "/b", new PointF(0.024, -0.266 + 5*0.0216));
	public final static Channel RECRUITMENT = new Channel("Recruitment", "/r", new PointF(0.024, -0.266 + 6*0.0216));
	public final static Channel PRIVATE = new Channel("Private", "/w", new PointF(0.024, -0.266 + 7*0.0216));
	public final static Channel INFORMATION = new Channel("Information", null, new PointF(0.024, -0.266 + 8*0.0216));
	public final static Channel FIGHT = new Channel("Fight", null, new PointF(0.024, -0.266 + 9*0.0216));
	public final static Channel PROMOTION = new Channel("Promotion", null, new PointF(0.024, -0.266 + 10*0.0216));
	public final static Channel KOLIZEUM = new Channel("Kolizeum", "/k", new PointF(0.024, -0.266 + 11*0.0216));
	public final static Channel EN_COMMUNITY = new Channel("EnCommunity", "/c", new PointF(0.024, -0.266 + 12*0.0216));
	
	  /**************/
	 /** ATRIBUTS **/
	/**************/
	
	private static Point chatMenuPosition;
	
	private String name;
	private String prefix;
	private PointF relativCheckPosition;
	
	  /*************/
	 /** BUILDER **/
	/*************/
	
	/** Constructeur de la classe {@code Channel}.
	 * @param name - Nom de la classe
	 * @param prefix - Pr�fix de la classe
	 * @param relativCheckPosition - Point relatif au menu
	 */
	public Channel(String name, String prefix, PointF relativCheckPosition) {
		this.name = name;
		this.prefix = prefix;
		this.relativCheckPosition = relativCheckPosition;
	}
	
	  /*************/
	 /** GETTERS **/
	/*************/
	
	/** Modifi la position du menu du chat.
	 * @param chatMenuPosition - Nouvelle position du menu du chat.
	 */
	public static void setChatMenuPosition(Point chatMenuPosition) {
		Channel.chatMenuPosition = chatMenuPosition;
	}
	
	/** Retourne le nom du canal.
	 * @return Nom du canal.
	 */
	public String getName() {
		return this.name;
	}
	
	/** Retourne le pr�fix du canal.
	 * @return Pr�fixe du canal.
	 */
	public String getPrefix() {
		return this.prefix;
	}
	
	  /*************/
	 /** PRIVATE **/
	/*************/
	
	/** Retourne l'�tat du canal.
	 * @param arrowPosition - Position du menu.
	 * @return {@code true} si le canal est affich�, {@code false} sinon.
	 */
	private boolean isDisplayed(PointF arrowPosition) {
		PointF checkPosition = new PointF(arrowPosition.x + relativCheckPosition.x, arrowPosition.y + relativCheckPosition.y);
		PointF checkTopLeft = new PointF(checkPosition.x, checkPosition.y - 0.005);
		PointF checkBottomRight = new PointF(checkPosition.x, checkPosition.y + 0.005);		
		return (B4D.screen.searchPixel(checkTopLeft, checkBottomRight, new Color(50,50,50), new Color(255,255,255)) != null);
	}
	
	/** Change l'�tat du canal.
	 * @param arrowPosition - Position du menu.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 */
	private void toggle(PointF arrowPosition) throws StopProgramException, CancelProgramException {
		PointF checkPosition = new PointF(arrowPosition.x + relativCheckPosition.x, arrowPosition.y + relativCheckPosition.y);		
		B4D.mouse.leftClick(checkPosition, false, 500);
	}
	
	/** Active/Affiche le canal.
	 * @param arrowPosition - Position du menu.
	 * @return {@code true} si l'�tat du canal a chang�, {@code false} sinon.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	private boolean enable(PointF arrowPosition) throws StopProgramException, CancelProgramException {
		if(!isDisplayed(arrowPosition)) {
			toggle(arrowPosition);
			return true;
		}
		else
			return false;
	}
	
	/** D�sactive/Cache le canal.
	 * @param arrowPosition - Position du menu.
	 * @return {@code true} si l'�tat du canal a chang�, {@code false} sinon.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 */
	private boolean disable(PointF arrowPosition) throws StopProgramException, CancelProgramException {
		if(isDisplayed(arrowPosition)) {
			toggle(arrowPosition);
			return true;
		}
		else
			return false;
	}
	
	  /************/
	 /** STATIC **/
	/************/
	
	 /** Retourne une liste de tous les canaux existants.
	 * @return List des canaux.
	 */
	public final static List<Channel> getAll(){
		  	List<Channel> channels = new ArrayList<Channel>();
		  	channels.add(GENERAL);
		  	channels.add(TEAM);
		  	channels.add(GUILD);
		  	channels.add(ALLIES);
		  	channels.add(GROUP);
		  	channels.add(BUSINESS);
		  	channels.add(RECRUITMENT);
		  	channels.add(PRIVATE);
		  	channels.add(INFORMATION);
		  	channels.add(FIGHT);
		  	channels.add(PROMOTION);
		  	channels.add(KOLIZEUM);
		  	channels.add(EN_COMMUNITY);
		    return channels;
		  }
	
	/** Permet d'afficher un ou plusieurs canaux. Les autres canaux seront d�sactiv�s.
	 * @param channel - Liste des canaux � afficher.
	 * @return Liste des canaux ayant chang� d'�tat.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 * @throws AWTException Si un probl�me de souris survient.
	 */
	public static List<Channel> displayChannels(Channel...channel) throws AWTException, StopProgramException, CancelProgramException {
		return displayChannels(Arrays.asList(channel));
	}
	
	/** Permet d'afficher un ou plusieurs canaux. Les autres canaux seront d�sactiv�s.
	 * @param channels - Liste des canaux � afficher.
	 * @return Liste des canaux ayant chang� d'�tat, {@code null} si impossible d'ouvrir le menu.
	 * @throws StopProgramException Si le programme est stopp�.
	 * @throws CancelProgramException Si le programme est annul�.
	 */
	public static List<Channel> displayChannels(List<Channel> channels) throws StopProgramException, CancelProgramException {
		List<Channel> toggles = null;
		
		if(chatMenuPosition != null) {
			PointF menu = B4D.converter.toPointF(chatMenuPosition);
			B4D.mouse.leftClick(menu, false, 200);		//Ouvre le menu du chat
			List<PointF> matchs = B4D.screen.searchPixels(new PointF(menu.x + 0.1984, menu.y - 0.2625), new PointF(menu.x + 0.1984, menu.y - 0.006), new Color(100, 100, 100), new Color(255, 255, 255));
			PointF arrowPosition = matchs.get(matchs.size() - 1);
			B4D.mouse.place(arrowPosition, 500);		//Affiche les caneaux affich�s
			
			toggles = new ArrayList<Channel>();
			
			for(Channel channel: getAll()) {
				boolean toggled;
				if(channels.contains(channel))
					toggled = channel.enable(arrowPosition);
				else
					toggled = channel.disable(arrowPosition);
				if(toggled)
					toggles.add(channel);
			}

			B4D.keyboard.sendKey(KeyEvent.VK_ESCAPE, 0);				//Ferme le menu du chat
			B4D.screen.waitForChangingPixel(arrowPosition, 10000);		//Attend la fermeture du menu
		}
		return toggles;
	}
	
	/** Permet de retourner le canal correspondant � un octet.
	 * @param data - Octet du canal.
	 * @return Canal correspondant.
	 * @throws B4DException Si une exception de type B4D est lev�e.
	 */
	public static Channel fromByte(byte data) throws B4DException {
		switch(Byte.toUnsignedInt(data)) {
			case(0):
				return GENERAL;
			case(4):
				return TEAM;
			case(0x09):
				return PRIVATE;
			case(5):
				return BUSINESS;
			case(6):
				return RECRUITMENT;
			default:
				throw new B4DException("Unknow channel " + Byte.toUnsignedInt(data));
		}
	}
	
	  /***************/
	 /** TO STRING **/
	/***************/
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return name;
	}
}
