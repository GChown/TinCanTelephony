package net.medsouz.tct.gui.window;

import net.medsouz.tct.api.FriendManager;
import net.medsouz.tct.api.objects.Friend;
import net.medsouz.tct.gui.GuiOverlay;
import net.medsouz.tct.gui.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import cpw.mods.fml.common.Loader;

/**
 * @author medsouz
 *
 */
public class WindowProfile extends Window {
	boolean isSelf;
	boolean areFriends;
	String username;
	GuiButton addFriend = new GuiButton(1, 0, 0, 80, 20, "Add Friend");
	GuiButton sendMessage = new GuiButton(1, 0, 0, 80, 20, "Send Message");
	GuiButton join = new GuiButton(1, 0, 0, 70, 20, "Join Server");
	GuiButton invite = new GuiButton(1, 0, 0, 40, 20, "Invite");
	GuiButton viewMods = new GuiButton(1, 0, 0, 80, 20, "View Mods");
	GuiButton groups = new GuiButton(1, 0, 0, 80, 20, "View Groups");
	GuiButton settings = new GuiButton(1, 0, 0, 90, 20, "Privacy Settings");
	
	public WindowProfile(GuiOverlay g, int x, int y, int w, int h, String user) {
		super(g, "Profile - "+user, x, y, w, h);		
		if(user.equals(this.overlay.username)){
			isSelf=true;
			this.setTitle("Profile - " + user + " (you!)");
			buttonList.add(settings);
		}
		else{
		buttonList.add(addFriend);
		buttonList.add(sendMessage);
		buttonList.add(join);
		buttonList.add(invite);
		buttonList.add(viewMods);
		buttonList.add(groups);
		}
		if(FriendManager.getFriends().contains(new Friend(user, "Ingame", true))){
			areFriends = true;
		}else{
			areFriends = false;
		}
		username = user;
	}

	@Override
	public void drawWindowContents() {
		if(isSelf){
			overlay.drawString(Minecraft.getMinecraft().fontRenderer, "This is you, " + username + "!", xPos + 5, yPos + 5, 0xFFFFFF);
			RenderHelper.drawPlayer(username, (int) (xPos + width - (width * 0.1F) - 10), (int) (yPos + (width * 0.2F)), width * 0.2F);
			settings.xPosition = xPos+5;
			settings.yPosition = yPos + 26;
		}
		else{
			RenderHelper.drawPlayer(username, (int) (xPos + width - (width * 0.1F) - 10), (int) (yPos + (width * 0.2F)), width * 0.2F);
			overlay.drawString(Minecraft.getMinecraft().fontRenderer, "\u00A7a\u00A7lOnline\u00A7f - Multiplayer", xPos + 5, yPos + 5, 0xFFFFFF);
			overlay.drawString(Minecraft.getMinecraft().fontRenderer, "\u00A7oPlaying on 127.0.0.1", xPos + 5, yPos + 15, 0xFFFFFF);
			join.xPosition = xPos + 5;
			join.yPosition = yPos + 26;
			invite.xPosition = xPos + 80;
			invite.yPosition = yPos + 26;
			//TODO: obtain these values from the server
			overlay.drawString(Minecraft.getMinecraft().fontRenderer, "Playing \u00A7l"+Loader.instance().getMCVersionString()+"\u00A7f", xPos + 5, yPos + 50, 0xFFFFFF);
			overlay.drawString(Minecraft.getMinecraft().fontRenderer, "Currently using \u00A7l"+Loader.instance().getModList().size()+"\u00A7f mods", xPos + 5, yPos + 60, 0xFFFFFF);
			viewMods.xPosition = xPos + 5;
			viewMods.yPosition = yPos + 70;
			groups.xPosition = xPos + 90;
			groups.yPosition = yPos + 70;
			sendMessage.xPosition = xPos + 5;
			sendMessage.yPosition = yPos + height - 22;
			addFriend.xPosition = xPos + 90;
			addFriend.yPosition = yPos + height - 22;
			if(areFriends){
				addFriend.enabled = false;
			}
		}
		
	}

	@Override
	public void onClose() {
		
	}

	@Override
	public void onButtonPress(GuiButton button) {
		if(button.equals(addFriend)){
			FriendManager.addFriend(username);
		}
		if(button.equals(settings)){
			for(Window w : this.overlay.windows) {
				if(w instanceof WindowSettings) {
					return;
				}
			}
			this.overlay.openWindow(new WindowSettings(this.overlay, username, (this.overlay.width / 2) - (175 / 2), (this.overlay.height / 2) - (140 / 2), 175, 140));
			
		}
	}

	public String getUsername() {
		return username;
	}

	@Override
	public void keyTyped(char c, int id) {
		
	}

}
