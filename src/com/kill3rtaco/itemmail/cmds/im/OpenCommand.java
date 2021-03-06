package com.kill3rtaco.itemmail.cmds.im;

import org.bukkit.entity.Player;

import com.kill3rtaco.itemmail.ItemMailConstants;
import com.kill3rtaco.itemmail.ItemMailMain;
import com.kill3rtaco.itemmail.mail.ItemMail;
import com.kill3rtaco.itemmail.mail.ItemMailBox;
import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class OpenCommand extends TacoCommand {

	public OpenCommand() {
		super("open", new String[]{"o"}, "[id]", "Open ItemMail", ItemMailConstants.P_OPEN_MAIL);
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		ItemMailBox mb = new ItemMailBox(player);
		int id = mb.getUnreadCount();
		if(args.length > 0){
			if(TacoAPI.getChatUtils().isNum(args[0])){
				id = Integer.parseInt(args[0]);
			}else{
				ItemMailMain.plugin.chat.sendPlayerMessage(player, "&e" + args[0] + " &cis not a valid number");
				return;
			}
		}
		ItemMail mail = mb.get(id - 1);
		if(mail == null){
			ItemMailMain.plugin.chat.sendPlayerMessage(player, "&cThat mail doesn't exist");
		}else{
			if(TacoAPI.getPlayerAPI().getInventoryAPI().giveItems(player, mail.getItems())){
				mail.open();
				ItemMailMain.plugin.chat.sendPlayerMessage(player, "&aMail opened");
			}else{
				String display = ItemMailMain.plugin.getDisplayString(mail.getItems());
				ItemMailMain.plugin.chat.sendPlayerMessage(player, "&cYou don't have enough room for &2" + mail.getItemAmount() + display);
			}
		}
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return false;
	}

}
