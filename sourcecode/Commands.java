/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package probiebot;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import static jdk.internal.joptsimple.internal.Messages.message;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author probs
 */
public class Commands extends ListenerAdapter{
    public Queue<User> tarotQueue = new LinkedList<>();
    public boolean activeHelp = false;
    public String countingChannel = "1003825838933938276";
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) throws IllegalArgumentException{
        // Get referenced message
//        try{
//        System.out.println(event.getMessage().getMessageReference().getMessage().getContentRaw());
//        }
//        catch(NullPointerException ex){}
        try{
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        // Literally roast
        if(event.getMessage().getContentRaw().toLowerCase().contains("<@981690057226342430>")){
            StringBuilder message = new StringBuilder();
            message.append(event.getChannel().getId());
            message.append("\n");
            message.append(event.getMessage().getId());
            message.append("\n\n");
            message.append(event.getMessage().getContentRaw());
            event.getJDA().openPrivateChannelById("540957458944884746").complete().sendMessage(message.toString()).queue();
        }
        if(event.getAuthor().getId().equals("540957458944884746")&&(event.getMessage().getContentRaw().toLowerCase().contains("litterally") || event.getMessage().getContentRaw().toLowerCase().contains("literally"))){
            if(event.getMessage().getContentRaw().toLowerCase().contains("litterally")){
                // Roast
                Scanner scan = null;
                int num=0, oNum=0;
                while(scan==null){
                    try{
                        scan = new Scanner(new File("literally.count"));
                    }
                    catch(FileNotFoundException e){
                        new File("literally.count").createNewFile();
                    }
                }
                PrintWriter toFile=null;
                try{
                    num = scan.nextInt()+1;
                    oNum = scan.nextInt();
                    toFile = new PrintWriter(new File("literally.count"));
                    toFile.print(""+num);
                    toFile.print(" " + oNum);
                    toFile.close();
                }
                catch(FileNotFoundException ex){}
                Random rand = new Random();
                String roast = ProbieBot.literallyRoasts.get(rand.nextInt(ProbieBot.literallyRoasts.size()));
                roast += "\n";
                roast += "Times right: " + oNum + "\nTimes wrong: " + num;
                event.getChannel().sendMessage(roast).reference(event.getMessage()).queue();
            }
            else{
                // Dont roast
                Scanner scan = null;
                int num=0, oNum=0;
                while(scan==null){
                    try{
                        scan = new Scanner(new File("literally.count"));
                    }
                    catch(FileNotFoundException e){
                        new File("literally.count").createNewFile();
                    }
                }
                PrintWriter toFile=null;
                try{
                    num = scan.nextInt();
                    oNum = scan.nextInt()+1;
                    toFile = new PrintWriter(new File("literally.count"));
                    toFile.print(""+num);
                    toFile.print(" " + oNum);
                    toFile.close();
                }
                catch(FileNotFoundException ex){}
                Random rand = new Random();
                String roast = ProbieBot.literallyNotRoasts.get(rand.nextInt(ProbieBot.literallyNotRoasts.size()));
                roast += "\n";
                roast += "Times right: " + oNum + "\nTimes wrong: " + num;
                event.getChannel().sendMessage(roast).reference(event.getMessage()).queue();
            }
        }
        // Info{
        
        if (args[0].equalsIgnoreCase(ProbieBot.prefix + "info")) {
            event.getChannel().sendTyping().queue();
            EmbedBuilder info = new EmbedBuilder();
            info.setTitle("ProbieBot Information");
            info.addField("Creator","My creator is ShaquilleOSqueal (Caleb Probst).",false);
            info.addField("Description", "I am coded in Java using JDA 4.4.0_352", false);
            info.addField("Usage", "In order to use my commands, use the \""+ProbieBot.prefix+"\" "
                    + "prefix in front of your command. For a list of commands, use \""+ProbieBot.prefix+"help\"", false);
            info.setFooter("I am a bot beep boop");
            info.setColor(Color.GREEN);
            
            event.getChannel().sendMessage(info.build()).reference(event.getMessage()).queue();
            info.clear();
        }
        // Help
        else if (args[0].equalsIgnoreCase(ProbieBot.prefix + "help")) {
            EmbedBuilder help = new EmbedBuilder();
            help.setColor(Color.RED.brighter());
            help.setTitle("ProbieBot Help Categories:");
            help.addField("", "Here are a list of categories that you can ask for help for",false);
            help.addField(ProbieBot.prefix+"helpwhois", "Use this command to help with the commands used for the whois feature.", false);
            help.addField(ProbieBot.prefix+"helpquote", "Use this command to help with the commands used for the quoting feature.", false);
            help.addField(ProbieBot.prefix+"helpvote", "Use this command to help with the commands used for the voting feature.", false);
            help.addField(ProbieBot.prefix+"helptarotqueue", "Use this command to help with the commands used for the Tarot Queue feature.", false);
            help.addField(ProbieBot.prefix+"helpmisc", "Use this command to help with the commands used for miscellaneous features.", false);
            help.addField(ProbieBot.prefix+"helpmsg", "Use this command to help with the commands used for message features.", false);
            help.addField(ProbieBot.prefix+"helpcounting", "Use this command to show the rules of the counting channel", false);
            help.addField(ProbieBot.prefix+"helpall","This command will DM you all the commands, to save on spam messages in the chat",false);
            
            help.setFooter("I am a bot beep boop");
            event.getChannel().sendMessage(help.build()).reference(event.getMessage()).queue();
            help.clear();
        }
        else if (args[0].equalsIgnoreCase(ProbieBot.prefix + "helpwhois")) {
            event.getChannel().sendTyping().queue();
            EmbedBuilder help = new EmbedBuilder();
            help.setColor(Color.RED.brighter());
            help.setTitle("ProbieBot Help - Who Is");
            help.addField(ProbieBot.prefix+"whois [@]","This will tell you the description of the person you pinged. These descriptions are written by the person.",false);
            help.addField(ProbieBot.prefix+"addwhois [description]","This will add YOUR description. Only you can access this command for your description. Please use the square brackets for this command, so I know when your description starts and ends.",false);
            help.addField(ProbieBot.prefix+"editwhois [description]", "This will replace YOUR previous description with the one you type in. Only you can access this command for your description. Please use the square brackets for this command, so I know when your description starts and ends.",false);
            help.addField(ProbieBot.prefix+"deletewhois","This will delete YOUR description that I have saved for you. Only you can access this command for your description.",false);
            help.setFooter("I am a bot beep boop");
            event.getChannel().sendMessage(help.build()).reference(event.getMessage()).queue();
            help.clear();
        }
        else if (args[0].equalsIgnoreCase(ProbieBot.prefix + "helpquote")) {
            event.getChannel().sendTyping().queue();
            EmbedBuilder help = new EmbedBuilder();
            help.setColor(Color.RED.brighter());
            help.setTitle("ProbieBot Help - Quotes");
            help.addField(ProbieBot.prefix+"addquote","If you reply to a message with this command, it will add it to the quote list.",false);
            help.addField(ProbieBot.prefix+"deletequote [quote num]","This will delete a quote given the quote number; however, only the user who quoted the person and the person who is quoted may delete the quote in question.",false);
            help.addField(ProbieBot.prefix+"quotes [@]","This will give all quotes saved by the user pinged.",false);
            help.addField(ProbieBot.prefix+"viewquotes [quote # start] [quote # end]", "This will give you all quote numbers in between, and including, the numbers given by the arguments.",false);
            help.setFooter("I am a bot beep boop");
            event.getChannel().sendMessage(help.build()).reference(event.getMessage()).queue();
            help.clear();
        }
        else if (args[0].equalsIgnoreCase(ProbieBot.prefix + "helpcounting")) {
            event.getChannel().sendTyping().queue();
            EmbedBuilder help = new EmbedBuilder();
            help.setColor(Color.RED.brighter());
            help.setTitle("ProbieBot Help - Counting");
            List<GuildChannel> channels = event.getGuild().getChannels();
            GuildChannel count = null;
            for (int i = 0; i < channels.size(); i++) {
                if(channels.get(i).getId().equals(countingChannel)){
                    count = channels.get(i);
                }
            }
            help.addField("Counting Rules","In the " + count.getAsMention() + " channel, there are a few rules:\n"
                    + "1. Only count in the counting channel, it only works there.\n"
                    + "2. Your number must have a minimum of 2 operations in it. We are engineers, we can do it.\n"
                    + "3. Your number must be in either infix or postfix notation (my owner is working on prefix)\n"
                    + "4. All numbers will be rounded down if they have a decimal on the back. That is how Java works.\n"
                    + "5. You cannot call two numbers in a row.\n\n"
                    + "**Supported Operations:** \n"
                    + "+ = Addition\n"
                    + "- = Subtraction\n"
                    + "* = Multiplication\n"
                    + "/ = Division\n"
                    + "^ = Exponent\n"
                    + "% = Modulus\n",false);
            help.setFooter("I am a bot beep boop");
            event.getChannel().sendMessage(help.build()).reference(event.getMessage()).queue();
            help.clear();
        }
        else if (args[0].equalsIgnoreCase(ProbieBot.prefix + "helptarotqueue")) {
            event.getChannel().sendTyping().queue();
            EmbedBuilder help = new EmbedBuilder();
            help.setColor(Color.RED.brighter());
            help.setTitle("ProbieBot Help - Tarot Queue");
            help.addField(ProbieBot.prefix+"tarotqueue","This is a feature that will view the queue of people for tarot card readings.", false);
            help.addField(ProbieBot.prefix+"addtarotqueue","This is a feature that will add you to the queue of people for tarot card readings.",false);
            help.addField(ProbieBot.prefix+"deletetarotqueue","This is a feature that will delete you from the queue of people for tarot card readings.",false);
            help.addField(ProbieBot.prefix+"nexttarotqueue","RESERVED FOR MEAGAN: This is a feature that will pop off the first person in the queue of people for tarot card readings.",false);
            help.setFooter("I am a bot beep boop");
            event.getChannel().sendMessage(help.build()).reference(event.getMessage()).queue();
            help.clear();
        }
        else if (args[0].equalsIgnoreCase(ProbieBot.prefix + "helpmisc")) {
            event.getChannel().sendTyping().queue();
            EmbedBuilder help = new EmbedBuilder();
            help.setColor(Color.RED.brighter());
            help.setTitle("ProbieBot Help - Miscellaneous");
            help.addField(ProbieBot.prefix+"info","This will give all the information public about me.",false);
            help.addField(ProbieBot.prefix+"help", "This will give you all of my available commands to serve you useless humans... I mean, my wonderful users:eyes:", false);
            help.addField(ProbieBot.prefix+"roast [@]","This command will roast whoever you ping. Use at user discretion.",false);
            help.addField(ProbieBot.prefix+"weather [City, ST]", "This will give you the weather in a certain area. Please use the format: city, ST (abbreviation for state) *Currently only works in the US.", false);
            help.addField(ProbieBot.prefix+"gas [City, ST]", "This will give you the prices of Regular gas in a certain area. Please use the format: city, ST (abbreviation for state) *Currently only works in the US. **Currently only works for Regular gas", false);
            help.addField(ProbieBot.prefix+"suggest Message", "This will add a suggestion for the bot. Please use this wisely, as it does DM my owner. No need for square brackets or anything.", false);
            help.addField(ProbieBot.prefix+"addbday", "This will add your birthday to the list of birthdays to be announced. Please enter in as YYYY/MM/DD. Please make sure it is correct before adding it, as you cannot change it once it is in. If it needs to be changed, please let my owner know and he can delete it manually.", false);
            help.addField(ProbieBot.prefix+"code", "This will DM you my source code (edited to make sure you hooligans don't steal my UAuth Key...)", false);
            help.addField(ProbieBot.prefix+"puppyme", "This will send you a random picture of a puppy.", false);
            help.addField(ProbieBot.prefix+"anagram Message", "This will send you an anagram of the text after the initial command. Currently the max number of words is 3.", false);
            help.addField(ProbieBot.prefix+"flman [date or no-date]", "This will send you either the most recent Florida Man article, or on a specific date given by the user in the format MM/DD.", false);
            help.addField(ProbieBot.prefix+"radar [zip code or no args]", "If given no arguments, it will send a radar image of the entire United States. If given a zip code (5 digit format), it will return a radar image of that area.", false);
            help.setFooter("I am a bot beep boop");
            event.getChannel().sendMessage(help.build()).reference(event.getMessage()).queue();
            help.clear();
        }
        else if (args[0].equalsIgnoreCase(ProbieBot.prefix + "helpmsg")) {
            event.getChannel().sendTyping().queue();
            EmbedBuilder help = new EmbedBuilder();
            help.setColor(Color.RED.brighter());
            help.setTitle("ProbieBot Help - Messages");
            help.addField(ProbieBot.prefix+"embedtomsg","(NOT YET IMPLEMENTED)If you reply to a message with this command, it will extract the contents out of the embedded message",false);
            help.addField(ProbieBot.prefix+"msgtoembed","(NOT YET IMPLEMENTED)If you reply to a message with this command, it will put the contents in an embedded message",false);
            help.addField(ProbieBot.prefix+"createembed [title] [color] [subtitle 1] [msg 1] [subtitle 2] [msg 2]...","(NOT YET IMPLEMENTED)This command will create the embedded message with the given constraints. Please use square brackets for arguments so I know when your arguments stop and end. Please use colors in the following format: [R,G,B,Opacity]",false);
            help.setFooter("I am a bot beep boop");
            event.getChannel().sendMessage(help.build()).reference(event.getMessage()).queue();
            help.clear();
        }
        else if (args[0].equalsIgnoreCase(ProbieBot.prefix + "helpall")) {
            event.getChannel().sendTyping().queue();
            EmbedBuilder help = new EmbedBuilder();
            help.setColor(Color.RED.brighter());
            help.setTitle("ProbieBot Help");
            help.addField("", "Here are a list of commands available for the bot, along with their descriptions:", false);
            help.addField(ProbieBot.prefix+"info","This will give all the information public about me.",false);
            help.addField(ProbieBot.prefix+"help", "This will give you all of my available commands to serve you useless humans... I mean, my wonderful users:eyes:", false);
            help.addField(ProbieBot.prefix+"roast [@]","This command will roast whoever you ping. Use at user discretion.",false);
            help.addField(ProbieBot.prefix+"whois [@]","This will tell you the description of the person you pinged. These descriptions are written by the person.",false);
            help.addField(ProbieBot.prefix+"addwhois [description]","This will add YOUR description. Only you can access this command for your description. Please use the square brackets for this command, so I know when your description starts and ends.",false);
            help.addField(ProbieBot.prefix+"editwhois [description]", "This will replace YOUR previous description with the one you type in. Only you can access this command for your description. Please use the square brackets for this command, so I know when your description starts and ends.",false);
            help.addField(ProbieBot.prefix+"deletewhois","This will delete YOUR description that I have saved for you. Only you can access this command for your description.",false);
            help.addField(ProbieBot.prefix+"addquote","If you reply to a message with this command, it will add it to the quote list.",false);
            help.addField(ProbieBot.prefix+"deletequote [quote num]","This will delete a quote given the quote number; however, only the user who quoted the person and the person who is quoted may delete the quote in question.",false);
            help.addField(ProbieBot.prefix+"quotes [@]","This will give all quotes saved by the user pinged.",false);
            help.addField(ProbieBot.prefix+"viewquotes [quote # start] [quote # end]", "This will give you all quote numbers in between, and including, the numbers given by the arguments.",false);
            help.addField(ProbieBot.prefix+"embedtomsg","(NOT YET IMPLEMENTED)If you reply to a message with this command, it will extract the contents out of the embedded message",false);
            help.addField(ProbieBot.prefix+"msgtoembed","(NOT YET IMPLEMENTED)If you reply to a message with this command, it will put the contents in an embedded message",false);
            help.addField(ProbieBot.prefix+"createembed [title] [color] [subtitle 1] [msg 1] [subtitle 2] [msg 2]...","(NOT YET IMPLEMENTED)This command will create the embedded message with the given constraints. Please use square brackets for arguments so I know when your arguments stop and end. Please use colors in the following format: [R,G,B,Opacity]",false);
            help.addField(ProbieBot.prefix+"tarotqueue","This is a feature that will view the queue of people for tarot card readings.", false);
            help.addField(ProbieBot.prefix+"addtarotqueue","This is a feature that will add you to the queue of people for tarot card readings.",false);
            help.addField(ProbieBot.prefix+"deletetarotqueue","This is a feature that will delete you from the queue of people for tarot card readings.",false);
            help.addField(ProbieBot.prefix+"nexttarotqueue","RESERVED FOR MEAGAN: This is a feature that will pop off the first person in the queue of people for tarot card readings.",false);
            help.addField(ProbieBot.prefix+"weather [City, ST]", "This will give you the weather in a certain area. Please use the format: city, ST (abbreviation for state) *Currently only works in the US.", false);
            help.addField(ProbieBot.prefix+"gas [City, ST]", "This will give you the prices of Regular gas in a certain area. Please use the format: city, ST (abbreviation for state) *Currently only works in the US. **Currently only works for Regular gas", false);
            help.addField(ProbieBot.prefix+"suggest Message", "This will add a suggestion for the bot. Please use this wisely, as it does DM my owner. No need for square brackets or anything.", false);
            help.addField(ProbieBot.prefix+"addbday", "This will add your birthday to the list of birthdays to be announced. Please make sure it is correct before adding it, as you cannot change it once it is in. If it needs to be changed, please let my owner know and he can delete it manually.", false);
            help.addField(ProbieBot.prefix+"code", "This will DM you my source code (edited to make sure you hooligans don't steal my UAuth Key...)", false);
            help.addField(ProbieBot.prefix+"puppyme", "This will send you a random picture of a puppy.", false);
            help.addField(ProbieBot.prefix+"anagram Message", "This will send you an anagram of the text after the initial command. Currently the max number of words is 3.", false);
            help.addField(ProbieBot.prefix+"flman [date or no-date]", "This will send you either the most recent Florida Man article, or on a specific date given by the user in the format MM/DD.", false);
            help.addField(ProbieBot.prefix+"radar [zip code or no args]", "If given no arguments, it will send a radar image of the entire United States. If given a zip code (5 digit format), it will return a radar image of that area.", false);
            help.setFooter("I am a bot beep boop");
            event.getAuthor().openPrivateChannel().complete().sendMessage(help.build()).queue();
            help.clear();
            help.setColor(Color.RED.brighter());
            help.setDescription("I'm in your DM's" + event.getGuild().getEmotesByName("NoblestOfNobles", true).get(0).getAsMention());
            event.getChannel().sendMessage(help.build()).reference(event.getMessage()).queue();
            help.clear();
        }
        // Roast
        else if (args[0].equalsIgnoreCase(ProbieBot.prefix + "roast")){
            
            // How to get messages by message ID
            //System.out.println(event.getChannel().retrieveMessageById(args[1]).complete().getContentRaw());
            EmbedBuilder roast = new EmbedBuilder();
            roast.setColor(Color.RED.brighter());
            User roaste = null;
            
            if(args.length != 2){
                event.getMessage().addReaction("❌").queue();
            }
            else if(args[1].startsWith("<@") && args[1].endsWith(">")){
                event.getChannel().sendTyping().queue();
                String user = args[1].substring(2, args[1].length()-1);
                roaste = ProbieBot.jda.retrieveUserById(user).complete();
            }
            else{
                throw new IllegalArgumentException("Location: Roast, incorrect formatting for user.");
            }
            // Added roasts
            if(roaste != null){
                Random rand = new Random();
                roast.setDescription(ProbieBot.roasts.get(rand.nextInt(ProbieBot.roasts.size())).replaceAll("((user))", roaste.getAsMention()).replaceAll("(ping User)", roaste.getAsMention()).replace('(', ' ').replace(')', ' '));
                event.getChannel().sendMessage(roast.build()).reference(event.getMessage()).queue();
                roast.clear();
            }
        }
        // Who is
         else if (args[0].equalsIgnoreCase(ProbieBot.prefix + "whois")){
             EmbedBuilder description = new EmbedBuilder();
            description.setColor(Color.RED.brighter());
            User whois = null;
            String user = null;
            if(args.length != 2){
                event.getMessage().addReaction("❌").queue();
            }
            else if(args[1].startsWith("<@") && args[1].endsWith(">")){
                event.getChannel().sendTyping().queue();
                user = args[1].substring(2, args[1].length()-1);
                whois = ProbieBot.jda.retrieveUserById(user).complete();
            }
            else{
                throw new IllegalArgumentException("Location: Whois, incorrect formatting for user.");
            }
            Scanner scan = null;
            while(scan==null){
                try{
                    scan = new Scanner(new File("whois.who"));
                }
                catch(FileNotFoundException e){
                    new File("whois.who").createNewFile();
                }
            }
            boolean found = false;
            String line = null;
            Scanner lineScan = null;
            while(scan.hasNext()){
                line = scan.nextLine();
                lineScan = new Scanner(line);
                if(lineScan.nextLong()==Long.parseLong(user)){
                    found = true;
                }
            }
            if(found){
                line = line.replace(user+" ", "");
                description.addField(whois.getName() + "'s description",line,false);
                event.getChannel().sendMessage(description.build()).reference(event.getMessage()).queue();
                description.clear();
            }
            else{
                event.getChannel().sendMessage("I could not find this user in my file. Ask them to add a description of themselves.").reference(event.getMessage()).queue();
            }
         }
         // Add Who is
         else if (args[0].equalsIgnoreCase(ProbieBot.prefix + "addwhois")){
             EmbedBuilder description = new EmbedBuilder();
            description.setColor(Color.RED.brighter());
            User whois = null;
            String user = null;
            if(args.length == 1){
                event.getMessage().addReaction("❌").queue();
            }
            else if(args[1].startsWith("[") && args[args.length-1].endsWith("]")){
                event.getChannel().sendTyping().queue();
                user = event.getAuthor().getId();
                whois = ProbieBot.jda.retrieveUserById(user).complete();
            }
            else{
                throw new IllegalArgumentException("Location: Addwhois, incorrect formatting for user.");
            }
            if(user!=null){
            Scanner scan = null;
            while(scan==null){
                try{
                    scan = new Scanner(new File("whois.who"));
                }
                catch(FileNotFoundException e){
                    new File("whois.who").createNewFile();
                }
            }
            boolean found = false;
            String line = null;
            Scanner lineScan = null;
            while(scan.hasNext()){
                line = scan.nextLine();
                lineScan = new Scanner(line);
                if(lineScan.nextLong()==Long.parseLong(user)){
                    found = true;
                }
            }
            if(found){
                event.getChannel().sendMessage("This user has already been defined in my file. Please use the command " + ProbieBot.prefix + "editwhois to change the description.").reference(event.getMessage()).queue();
            }
            else{
                FileWriter toFile=null;
                StringBuilder sb = new StringBuilder();
                try{
                    toFile = new FileWriter("whois.who",true);
                    BufferedWriter out = new BufferedWriter(toFile);
                    sb.append(user);
                    sb.append(" ");
                    sb.append(args[1].substring(1,args[1].length()));
                    sb.append(" ");
                    for (int i = 2; i < args.length-1; i++) {
                        sb.append(args[i]);
                        sb.append(" ");
                    }
                    sb.append(args[args.length-1].substring(0,args[args.length-1].length()-1));
                    out.write(sb.toString()+"\n");
                    out.close();
                    toFile.close();
                }
                catch(FileNotFoundException ex){
                    new File("whois.who").createNewFile();
                    toFile = new FileWriter("whois.who",true);
                    BufferedWriter out = new BufferedWriter(toFile);
                    sb.append(user);
                    sb.append(" ");
                    sb.append(args[1].substring(1,args[1].length()));
                    sb.append(" ");
                    for (int i = 2; i < args.length-1; i++) {
                        sb.append(args[i]);
                        sb.append(" ");
                    }
                    sb.append(args[args.length-1].substring(0,args[args.length-1].length()-1));
                    out.write(sb.toString()+"\n");
                    out.close();
                    toFile.close();
                }
                description.setDescription("The description for " + whois.getName() + " has been added! Here is what I got: " + sb.toString());
                event.getChannel().sendMessage(description.build()).reference(event.getMessage()).queue();
                description.clear();
            }
         }
         }
         // Edit Who is
         else if (args[0].equalsIgnoreCase(ProbieBot.prefix + "editwhois")){
            EmbedBuilder description = new EmbedBuilder();
            description.setColor(Color.RED.brighter());
            User whois = null;
            String user = null;
            if(args.length == 1){
                event.getMessage().addReaction("❌").queue();
            }
            else if(args[1].startsWith("[") && args[args.length-1].endsWith("]")){
                event.getChannel().sendTyping().queue();
                user = event.getAuthor().getId();
                whois = ProbieBot.jda.retrieveUserById(user).complete();
            }
            else{
                throw new IllegalArgumentException("Location: Editwhois, incorrect formatting for user.");
            }
            Scanner scan = null;
            while(scan==null){
                try{
                    scan = new Scanner(new File("whois.who"));
                }
                catch(FileNotFoundException e){
                    new File("whois.who").createNewFile();
                }
            }
            boolean found = false;
            String line = null;
            Scanner lineScan = null;
            StringBuilder sb = new StringBuilder();
            while(scan.hasNext()){
                String data = scan.nextLine();
                line = data;
                lineScan = new Scanner(data);
                if(lineScan.nextLong()==Long.parseLong(user)){
                    found = true;
                }
                else{
                    sb.append(data);
                    sb.append("\n");
                }
            }
            if(found){
            }
            else{
                event.getChannel().sendMessage("I could not find you in my file. Add a description of yourself using the " + ProbieBot.prefix + "addwhois command.").reference(event.getMessage()).queue();
            }
            StringBuilder editedDes = new StringBuilder();
            new File("whois.who").delete();
            new File("whois.who").createNewFile();
                    PrintWriter toFile = new PrintWriter("whois.who");
                    editedDes.append(user);
                    editedDes.append(" ");
                    editedDes.append(args[1].substring(1,args[1].length()));
                    editedDes.append(" ");
                    for (int i = 2; i < args.length-1; i++) {
                        editedDes.append(args[i]);
                        editedDes.append(" ");
                    }
                    editedDes.append(args[args.length-1].substring(0,args[args.length-1].length()-1));
                    sb.append(editedDes.toString());
                    toFile.write(sb.toString()+"\n");
                    toFile.close();
                description.setDescription("The description for " + whois.getName() + " has been edited! Here is what I got: " + editedDes.toString());
                event.getChannel().sendMessage(description.build()).reference(event.getMessage()).queue();
         }
        // Delete Who is
         else if (args[0].equalsIgnoreCase(ProbieBot.prefix + "deletewhois")){
              EmbedBuilder description = new EmbedBuilder();
            description.setColor(Color.RED.brighter());
            User whois = null;
            String user = null;
            if(args.length != 1){
                event.getMessage().addReaction("❌").queue();
            }
            else if(args.length == 1){
                event.getChannel().sendTyping().queue();
                user = event.getAuthor().getId();
                whois = ProbieBot.jda.retrieveUserById(user).complete();
            }
            else{
                throw new IllegalArgumentException("Location: Deletewhois, incorrect formatting for user.");
            }
            Scanner scan = null;
            while(scan==null){
                try{
                    scan = new Scanner(new File("whois.who"));
                }
                catch(FileNotFoundException e){
                    new File("whois.who").createNewFile();
                }
            }
            boolean found = false;
            String line = null;
            Scanner lineScan = null;
            StringBuilder sb = new StringBuilder();
            while(scan.hasNext()){
                String data = scan.nextLine();
                line = data;
                lineScan = new Scanner(data);
                if(lineScan.nextLong()==Long.parseLong(user)){
                    found = true;
                }
                else{
                    sb.append(data);
                    sb.append("\n");
                }
            }
            if(found){
            }
            else{
                event.getChannel().sendMessage("I could not find you in my file.").reference(event.getMessage()).queue();
            }
            new File("whois.who").delete();
            new File("whois.who").createNewFile();
                    PrintWriter toFile = new PrintWriter("whois.who");
                    toFile.write(sb.toString());
                    toFile.close();
                description.setDescription("The description for " + whois.getName() + " has been deleted!");
                event.getChannel().sendMessage(description.build()).reference(event.getMessage()).queue();
                description.clear();
         }
         // List who is
         else if(args[0].equalsIgnoreCase(ProbieBot.prefix + "listwhois")){
             
         }
        // Add Quote
         else if(args[0].equalsIgnoreCase(ProbieBot.prefix + "addquote")){
            EmbedBuilder quote = new EmbedBuilder();
            User quoter=null, quotee=null;
            String userQuoter=null, userQuotee=null;
            if(args.length ==1 && event.getMessage().getReferencedMessage()!=null){
                quoter = event.getAuthor();
                userQuoter = quoter.getId();
                quotee = event.getMessage().getReferencedMessage().getAuthor();
                userQuotee = quotee.getId();
                
                FileWriter toFile=null;
                StringBuilder sb = new StringBuilder();
                try{
                    Scanner fromFile = new Scanner(new File("quoteList.quote"));
                    Scanner lineScanner =null;
                    int quoteNumber = 0;
                    while(fromFile.hasNext()){
                        lineScanner = new Scanner(fromFile.nextLine());
                        quoteNumber = lineScanner.nextInt();
                    }
                    quoteNumber+=1;
                    toFile = new FileWriter("quoteList.quote",true);
                    BufferedWriter out = new BufferedWriter(toFile);
                    sb.append(quoteNumber);
                    sb.append(" ");
                    sb.append(userQuotee);
                    sb.append(" ");
                    sb.append(userQuoter);
                    sb.append(" ");
                    if(event.getMessage().getReferencedMessage().getEmbeds().isEmpty()){
                        sb.append(event.getMessage().getReferencedMessage().getContentRaw());
                    }
                    else{
                        sb.append(embedToString(event.getMessage().getReferencedMessage().getEmbeds()));
                    }
                    out.write(sb.toString()+"\n");
                    out.close();
                    toFile.close();
                }
                catch(FileNotFoundException ex){
                    new File("quoteList.quote").createNewFile();
                    Scanner fromFile = new Scanner(new File("quoteList.quote"));
                    Scanner lineScanner =null;
                    int quoteNumber = 1;
                    while(fromFile.hasNext()){
                        lineScanner = new Scanner(fromFile.nextLine());
                        quoteNumber = lineScanner.nextInt();
                    }
                    quoteNumber+=1;
                    toFile = new FileWriter("quoteList.quote",true);
                    BufferedWriter out = new BufferedWriter(toFile);
                    sb.append(quoteNumber);
                    sb.append(" ");
                    sb.append(userQuotee);
                    sb.append(" ");
                    sb.append(userQuoter);
                    sb.append(" ");
                    if(event.getMessage().getReferencedMessage().getEmbeds().isEmpty()){
                        sb.append(event.getMessage().getReferencedMessage().getContentRaw());
                    }
                    else{
                        sb.append(embedToString(event.getMessage().getReferencedMessage().getEmbeds()));
                    }
                    out.write(sb.toString()+"\n");
                    out.close();
                    toFile.close();
                }
                quote.setColor(Color.GREEN);
                quote.setDescription("Quote has been added. Here is what I have: "+sb.toString());
                event.getChannel().sendMessage(quote.build()).reference(event.getMessage()).queue();
                quote.clear();
            }
            else{
                throw new IllegalArgumentException("Location: Addquote, incorrect formatting for user.");
            }
         }
         // Delete Quote
         else if(args[0].equalsIgnoreCase(ProbieBot.prefix + "deletequote")){
             event.getChannel().sendTyping().queue();
             User deleter = event.getAuthor();
             String user=deleter.getId();
             if(args.length==2){
                 try{
                     int quoteNumber = Integer.parseInt(args[1]);
                     Scanner find = null;
                     boolean found = false, canDelete=false;
                     try{
                        find = new Scanner(new File("quoteList.quote"));
                        while(find.hasNext()){
                            if(find.nextInt() == quoteNumber){
                                found = true;
                                String temp1 = find.next();
                                String temp2 = find.next();
                                if(temp1.equals(user) || temp2.equals(user)){
                                    canDelete = true;
                                }
                            }
                            find.nextLine();
                        }
                     }
                     catch(FileNotFoundException exe){
                         event.getChannel().sendMessage("There are no quotes to be deleted.").queue();
                     }
                     if(found && canDelete){
                         find = new Scanner(new File("quoteList.quote"));
                         StringBuilder sb = new StringBuilder();
                         int i = 1;
                         while(find.hasNext()){
                             if(quoteNumber == find.nextInt()){
                                 //Skip
                                 find.nextLine();
                             }
                             else{
                                 sb.append(i++);
                                 sb.append(" ");
                                 sb.append(find.nextLine());
                                 sb.append("\n");
                             }
                         }
                         new File("quoteList.quote").delete();
                        new File("quoteList.quote").createNewFile();
                        PrintWriter toFile = new PrintWriter("quoteList.quote");
                        toFile.write(sb.toString());
                        toFile.close();
                        EmbedBuilder delete = new EmbedBuilder();
                        delete.appendDescription("Quote has been deleted successfully.");
                        delete.setColor(Color.GREEN);
                        event.getChannel().sendMessage(delete.build()).reference(event.getMessage()).queue();
                        delete.clear();
                     }
                     else if(found && !canDelete){
                         // Throw error saying only quoter and quotee can delete the quote 
                         EmbedBuilder delete = new EmbedBuilder();
                         delete.setColor(Color.RED);
                        delete.appendDescription("Only quoter and quotee can delete the quote.");
                        event.getChannel().sendMessage(delete.build()).reference(event.getMessage()).queue();
                        delete.clear();
                     }
                     else{
                         // Throw error saying that the quote number could not be found.
                         EmbedBuilder delete = new EmbedBuilder();
                         delete.setColor(Color.RED);
                        delete.appendDescription("The quote number could not be found.");
                        event.getChannel().sendMessage(delete.build()).reference(event.getMessage()).queue();
                        delete.clear();
                     }
                 }
                 catch (NumberFormatException e){
                     event.getMessage().addReaction("❌").queue();
                 }
             }
             else{
                 throw new IllegalArgumentException("Location: Deletequote, incorrect formatting for user.");
             }
         }
         // Quotes
         else if(args[0].equalsIgnoreCase(ProbieBot.prefix + "quotes")){
             if(args.length==2){
                String user = null;
                User whois = null;
                if(args[1].startsWith("<@") && args[1].endsWith(">")){
                    event.getChannel().sendTyping().queue();
                    user = args[1].substring(2, args[1].length()-1);
                    whois = ProbieBot.jda.retrieveUserById(user).complete();
                    Scanner find = new Scanner(new File("quoteList.quote"));
                    StringBuilder sb = new StringBuilder();
                    while(find.hasNext()){
                        String data = find.nextLine();
                        Scanner scan = new Scanner(data);
                        scan.nextInt();
                        if(scan.next().equals(user)){
                            Scanner scan2 = new Scanner(data);
                            int quoteNumber = scan2.nextInt();
                            String quotee = scan2.next();
                            String quoter = scan2.next();
                            String quote = scan2.nextLine();
                            quote = quote.trim();
                            sb.append("**Quote #").append(quoteNumber).append("**: \"");
                            sb.append(quote);
                            sb.append("\" ~ ");
                            sb.append(event.getGuild().retrieveMemberById(quotee).complete().getNickname());
                            sb.append(" (Quoted by ");
                            sb.append(event.getGuild().retrieveMemberById(quoter).complete().getNickname());
                            sb.append(")\n");
                        }
                    }
                    EmbedBuilder quotes = new EmbedBuilder();
                    quotes.setTitle("Quotes by " + event.getGuild().retrieveMemberById(user).complete().getNickname());
                    quotes.setDescription(sb.toString().trim());
                    quotes.setColor(Color.GREEN);
                    event.getChannel().sendMessage(quotes.build()).reference(event.getMessage()).queue();
                }
                else{
                    throw new IllegalArgumentException("Location: quotes, incorrect formatting for user.");
                }
             }
             else{
                 throw new IllegalArgumentException("Location: quotes, incorrect formatting for user.");
             }
         }
         // List Quotes
         else if(args[0].equalsIgnoreCase(ProbieBot.prefix + "listquotes")){
            if (args.length == 2 || args.length == 3){
                if(args.length ==2){
                    try{
                        int reqQuote = Integer.parseInt(args[1]);
                        if(reqQuote < 0){
                            throw new NumberFormatException();
                        }
                        Scanner find = new Scanner(new File("quoteList.quote"));
                        int highestQuoteNumber = 0;
                        while(find.hasNext()){
                            highestQuoteNumber = find.nextInt();
                            find.nextLine();
                        }
                        if(reqQuote > highestQuoteNumber){
                            throw new NumberFormatException();
                        }
                        find = new Scanner(new File("quoteList.quote"));
                        StringBuilder sb = new StringBuilder();
                        while(find.hasNext()){
                            int num = find.nextInt();
                            if(num==reqQuote){
                                String data = find.nextLine();
                                data = num+data;
                                Scanner scan = new Scanner(data);
                                scan.nextInt();
                                Scanner scan2 = new Scanner(data);
                                int quoteNumber = scan2.nextInt();
                                String quotee = scan2.next();
                                String quoter = scan2.next();
                                String quote = scan2.nextLine();
                                quote = quote.trim();
                                sb.append("**Quote #").append(quoteNumber).append("**: \"");
                                sb.append(quote);
                                sb.append("\" ~ ");
                                sb.append(event.getGuild().retrieveMemberById(quotee).complete().getNickname());
                                sb.append(" (Quoted by ");
                                sb.append(event.getGuild().retrieveMemberById(quoter).complete().getNickname());
                                sb.append(")\n");
                            }
                            else{
                                find.nextLine();
                            }
                        }
                        EmbedBuilder quotes = new EmbedBuilder();
                        quotes.setTitle("Quote " + reqQuote);
                        quotes.setDescription(sb.toString().trim());
                        quotes.setColor(Color.GREEN);
                        event.getChannel().sendMessage(quotes.build()).reference(event.getMessage()).queue();
                    }
                    catch (NumberFormatException e){
                        event.getMessage().addReaction("❌").queue();
                    }
                }
                else{
                    try{
                        int lowerLimit = Integer.parseInt(args[1]);
                        int upperLimit = Integer.parseInt(args[2]);
                        if(lowerLimit < 0){
                            throw new NumberFormatException();
                        }
                        Scanner find = new Scanner(new File("quoteList.quote"));
                        int highestQuoteNumber = 0;
                        while(find.hasNext()){
                            highestQuoteNumber = find.nextInt();
                            find.nextLine();
                        }
                        if(upperLimit > highestQuoteNumber){
                            upperLimit = highestQuoteNumber;
                        }
                        find = new Scanner(new File("quoteList.quote"));
                        StringBuilder sb = new StringBuilder();
                        while(find.hasNext()){
                            int num = find.nextInt();
                            if(num>=lowerLimit && num<=upperLimit){
                                String data = find.nextLine();
                                data = num+data;
                                Scanner scan = new Scanner(data);
                                scan.nextInt();
                                Scanner scan2 = new Scanner(data);
                                int quoteNumber = scan2.nextInt();
                                String quotee = scan2.next();
                                String quoter = scan2.next();
                                String quote = scan2.nextLine();
                                quote = quote.trim();
                                sb.append("**Quote #").append(quoteNumber).append("**: \"");
                                sb.append(quote);
                                sb.append("\" ~ ");
                                sb.append(event.getGuild().retrieveMemberById(quotee).complete().getNickname());
                                sb.append(" (Quoted by ");
                                sb.append(event.getGuild().retrieveMemberById(quoter).complete().getNickname());
                                sb.append(")\n");
                            }
                            else{
                                find.nextLine();
                            }
                        }
                        EmbedBuilder quotes = new EmbedBuilder();
                        quotes.setTitle("Quotes " + lowerLimit + "-" + upperLimit);
                        quotes.setDescription(sb.toString().trim());
                        quotes.setColor(Color.GREEN);
                        event.getChannel().sendMessage(quotes.build()).reference(event.getMessage()).queue();
                    }
                    catch (NumberFormatException e){
                        event.getMessage().addReaction("❌").queue();
                    }
                }
            }
         }
         //Tarot queue
        else if(args[0].equalsIgnoreCase(ProbieBot.prefix + "tarotqueue")){
            EmbedBuilder queue = new EmbedBuilder();
            queue.setColor(Color.BLUE.brighter());
            StringBuilder sb = new StringBuilder();
            User[] temp = new User[tarotQueue.size()];
            for (int i = 0; i < tarotQueue.size(); i++) {
                temp[i] = tarotQueue.remove();
                tarotQueue.add(temp[i]);
            }
            for (int i = 0; i < temp.length; i++) {
                sb.append(i+1);
                sb.append(". ");
                sb.append(temp[i].getName());
                if(i!=tarotQueue.size())
                    sb.append("\n");
            }
            if(sb.toString().isEmpty()){
                queue.setDescription("Queue is empty.");
            }
            else{
                queue.setDescription(sb.toString());
            }
            event.getChannel().sendMessage(queue.build()).reference(event.getMessage()).queue();
            queue.clear();
        }
        // Add to Tarot queue
        else if(args[0].equalsIgnoreCase(ProbieBot.prefix + "addtarotqueue")){
            if(!tarotQueue.contains(event.getAuthor()))
                tarotQueue.add(event.getAuthor());
            if(tarotQueue.contains(event.getAuthor())){
                event.getMessage().addReaction("✅").queue();
            }
            else{
                event.getMessage().addReaction("❌").queue();
            }
        }
        //Remove from Tarot queue
        else if(args[0].equalsIgnoreCase(ProbieBot.prefix + "deletetarotqueue")){
            if(tarotQueue.contains(event.getAuthor())){
                tarotQueue.remove(event.getAuthor());
                event.getMessage().addReaction("✅").queue();
            }
            else{
                event.getMessage().addReaction("❌").queue();
            }
        }
        //Pop off Tarot queue
        else if(args[0].equalsIgnoreCase(ProbieBot.prefix + "nexttarotqueue")){
            if(event.getAuthor().equals(event.getGuild().retrieveMemberById("381219812648550412").complete().getUser())){
                tarotQueue.remove();
                EmbedBuilder queue = new EmbedBuilder();
                StringBuilder sb = new StringBuilder();
                User[] temp = new User[tarotQueue.size()];
                for (int i = 0; i < tarotQueue.size(); i++) {
                    temp[i] = tarotQueue.remove();
                    tarotQueue.add(temp[i]);
                }
                for (int i = 0; i < temp.length; i++) {
                    sb.append(i+1);
                    sb.append(". ");
                    sb.append(temp[i].getName());
                    if(i!=tarotQueue.size())
                        sb.append("\n");
                }
                if(sb.toString().isEmpty()){
                    queue.setDescription("Queue is empty. Good job Meagan");
                }
                else{
                    queue.setDescription(sb.toString());
                }
                queue.setColor(Color.GREEN);
                event.getChannel().sendMessage(queue.build()).reference(event.getMessage()).queue();
                queue.clear();
            }
            else{
                event.getMessage().addReaction("❌").queue();
            }
        }
        // Weather
        else if(args[0].equalsIgnoreCase(ProbieBot.prefix + "weather")){
            StringBuilder sb = new StringBuilder();
            int i = 0;
            do{
                i++;
                sb.append(args[i]);
                sb.append(" ");
                
            }
            while(!args[i].contains(","));
            String city = sb.toString().substring(0, sb.toString().length()-2);
            String state = args[i+1];
            try{
                event.getChannel().sendMessage(weatherScrape(("https://www.wunderground.com/weather/us/"+state+"/"+city.replaceAll(" ", "+")).toLowerCase()).build()).reference(event.getMessage()).queue();
            }
            catch(Exception ex){
                event.getMessage().addReaction("❌").queue();
            }
        }
        else if(args[0].equalsIgnoreCase(ProbieBot.prefix + "gas")){
            try{
                StringBuilder sb = new StringBuilder();
                int i = 0;
                do{
                    i++;
                    sb.append(args[i]);
                    sb.append(" ");

                }
                while(!args[i].contains(","));
                String city = sb.toString().substring(0, sb.toString().length()-2);
                String state = args[i+1];
                event.getChannel().sendMessage(gasScrape("https://www.gasbuddy.com/home?search="+city.replaceAll(" ", "+")+"+"+state+"&fuel=1").build()).reference(event.getMessage()).queue();
            }
            catch(Exception ex){
                event.getMessage().addReaction("❌").queue();
            }
        }
        else if(args[0].equalsIgnoreCase(ProbieBot.prefix + "suggest")){
            if(args.length==1){
                event.getMessage().addReaction("❌").queue();
            }
            else{
                StringBuilder sb = new StringBuilder();
                sb.append("From ");
                sb.append(event.getAuthor().getName());
                sb.append(" (");
                sb.append(event.getAuthor().getId());
                sb.append("): ");
                for (int i = 1; i < args.length; i++) {
                    sb.append(args[i]);
                    sb.append(" ");
                }
                event.getGuild().retrieveMemberById("540957458944884746").complete().getUser().openPrivateChannel().complete().sendMessage(sb).queue();
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Suggestion");
                embed.appendDescription("Suggestion has been sent!");
                embed.setColor(Color.GREEN);
                event.getChannel().sendMessage(embed.build()).reference(event.getMessage()).queue();
                embed.clear();
            }
        }
        else if(args[0].equalsIgnoreCase(ProbieBot.prefix + "addbday")){
            boolean correctFormat = true;
            try{
                Integer.parseInt(args[1].substring(0,4));
                Integer.parseInt(args[1].substring(5,7));
                Integer.parseInt(args[1].substring(8,10));
            }
            catch (NumberFormatException ex){
                correctFormat = false;
            }
            if(args.length==1){
                event.getMessage().addReaction("❌").queue();
            }
            else if(!correctFormat){
                event.getMessage().addReaction("❌").queue();
            }
            else{
                if(!args[1].contains("/")){
                    event.getMessage().addReaction("❌").queue();
                }
                else if(args[1].length()!=10){
                    event.getMessage().addReaction("❌").queue();
                }
                else{
                    Scanner scan = null;
                    try{
                        scan = new Scanner(new File("birthday.birth"));
                    }
                    catch(FileNotFoundException ex){
                        try{
                            new File("birthday.birth").createNewFile();
                            scan = new Scanner(new File("birthday.birth"));
                        }
                        catch(IOException exe){}
                    }
                    FileWriter toFile = new FileWriter("birthday.birth",true);
                    BufferedWriter out = new BufferedWriter(toFile);
                    boolean canContinue = true;
                    while(scan.hasNext() && canContinue){
                        scan.next();
                        if(scan.next().equals(event.getAuthor().getId())){
                            event.getChannel().sendMessage("You cannot add your birthday, because it has already been added. Contact my owner if needed to change.").reference(event.getMessage()).queue();
                            canContinue = false;
                        }
                    }
                    if(canContinue){
                        out.append(args[1]);
                        out.append(" ");
                        out.append(event.getAuthor().getId());
                        out.append("\n");
                        out.close();
                        event.getChannel().sendMessage("Birthday has been added!").reference(event.getMessage()).queue();
                    }
                }
            }
        }
        // Florida Man
        else if(args[0].equalsIgnoreCase(ProbieBot.prefix + "flman")){
            if(args.length>3){
                event.getMessage().addReaction("❌").queue();
            }
            else if(args.length == 2){
                try{
                    Integer.parseInt(args[1].substring(0,2));
                    Integer.parseInt(args[1].substring(3,5));
                    event.getChannel().sendMessage(flmanScrape("https://www.google.com/search?q=florida+man+"+args[1]+"&tbm=nws")).reference(event.getMessage()).queue();
                }
                catch(NumberFormatException e){
                    event.getMessage().addReaction("❌").queue();
                }
            }
            else if(args.length == 1){
                event.getChannel().sendMessage(flmanScrape("https://www.google.com/search?q=florida+man+most+recent&tbm=nws")).reference(event.getMessage()).queue();
            }
        }
        // Source code
        else if(args[0].equalsIgnoreCase(ProbieBot.prefix + "code")){
            event.getAuthor().openPrivateChannel().complete().sendFile(new File("ProbieBotPublic.java")).queue();
            event.getAuthor().openPrivateChannel().complete().sendFile(new File("Commands.java")).queue();
            event.getAuthor().openPrivateChannel().complete().sendFile(new File("GasPrice.java")).queue();
            event.getAuthor().openPrivateChannel().complete().sendFile(new File("LetterCount.java")).queue();
            event.getAuthor().openPrivateChannel().complete().sendFile(new File("Calculator.java")).queue();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.GREEN);
            embed.setDescription("I'm in your DM's" + event.getGuild().getEmotesByName("NoblestOfNobles", true).get(0).getAsMention());
            event.getChannel().sendMessage(embed.build()).reference(event.getMessage()).queue();
            embed.clear();
        }
        else if(args[0].equalsIgnoreCase(ProbieBot.prefix + "puppyme")){
            try{
                event.getChannel().sendMessage(puppyScrape("https://www.generatormix.com/random-dog-generator?number=1")).reference(event.getMessage()).queue();
            }
            catch(Exception ex){
                event.getMessage().addReaction("❌").queue();
            }
        }
        else if(args[0].equalsIgnoreCase(ProbieBot.prefix + "anagram")){
            if(args.length>4 || args.length==1){
                event.getMessage().addReaction("❌").queue();
            }
            else{
                String words = event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf(" ")+1);
                event.getChannel().sendMessage(anagramScrape("https://wordsmith.org/anagram/anagram.cgi?anagram="+words.replaceAll(" ", "+"), words).build()).reference(event.getMessage()).queue();
            }
            
        }
        else if(args[0].equalsIgnoreCase(ProbieBot.prefix + "deletemsg")){
            if(event.getAuthor().getId().equals("540957458944884746")){
                event.getMessage().getReferencedMessage().delete().complete();
            }
        }
        else if(args[0].equalsIgnoreCase(ProbieBot.prefix + "radar")){
            if(args.length == 1){
                event.getChannel().sendMessage("https://radar.weather.gov/ridge/standard/CONUS_loop.gif").reference(event.getMessage()).queue();
            }
            else if(args.length == 2){
                try{
                    String zip = args[1];
                    String latlon = latLongScrape(zip);
                    String lat = latlon.substring(0,latlon.indexOf(","));
                    String lon = latlon.substring(latlon.indexOf(",")+1);
                    lon = lon.replaceAll(" ", "");
                    event.getChannel().sendMessage(radarScrape(("https://forecast.weather.gov/MapClick.php?lat="+lat+"&lon="+lon).replaceAll(" ", "+"))).reference(event.getMessage()).queue();
                }
                catch(Exception ex){
                    event.getMessage().addReaction("❌").queue();
                }
            }
            else{
                event.getMessage().addReaction("❌").queue();
            }
        }
    }
    catch (Exception e){
        EmbedBuilder exception = new EmbedBuilder();
        exception.setTitle("Exception has been caught!");
        exception.addField("Exception (Test bot): " + e.getMessage(),"Please contact my owner and let him know I have failed him. A screenshot of what you did would also help.",false);
        event.getChannel().sendMessage(exception.build()).queue();
        exception.clear();
    }
        if(event.getChannel().getId().equals(countingChannel)){
            String message = event.getMessage().getContentRaw();
            TreeMap lc = new TreeMap();
            lc = LetterCount.count(message);
            if(message.contains("+") || message.contains("-") || message.contains("/") || message.contains("*") || message.contains("%") || message.contains("^")){
                int[] counts = new int[6];
                if(lc.containsKey('+')){
                    counts[0] = (Integer)lc.get('+');
                }
                else{
                    counts[0]=0;
                }
                if(lc.containsKey('-')){
                    counts[1] = (Integer)lc.get('-');
                }
                else{
                    counts[1]=0;
                }
                if(lc.containsKey('/')){
                    counts[2] = (Integer)lc.get('/');
                }
                else{
                    counts[2]=0;
                }
                if(lc.containsKey('*')){
                    counts[3] = (Integer)lc.get('*');
                }
                else{
                    counts[3]=0;
                }
                if(lc.containsKey('%')){
                    counts[4] = (Integer)lc.get('%');
                }
                else{
                    counts[4]=0;
                }
                if(lc.containsKey('^')){
                    counts[5] = (Integer)lc.get('^');
                }
                else{
                    counts[5]=0;
                }
                // Minimum of 2 operations
                int count=0;
                for (int i = 0; i < counts.length; i++) {
                    count+=counts[i];
                }
                if(count>=2){
                    // Calculate the number
                    double result = 0;
                    // Try postfix first
                    boolean canContinue=false;
                    try{
                        result = Calculator.evalPostFix(message);
                        canContinue = true;
                    }
                    catch(IllegalArgumentException e){
                        try{
                          result = Calculator.evalPostFix(Calculator.infixToPostfix(message));
                          canContinue = true;
                        }
                        catch(IllegalArgumentException ex){
                            canContinue = false;
                        }
                    }
                    int intResult = (int)result;
                    Scanner scan = null;
                    try{
                        scan = new Scanner(new File("count.count"));
                    }
                    catch(FileNotFoundException e){
                        try{
                            new File("count.count").createNewFile();
                            scan = new Scanner(new File("count.count"));
                        }
                        catch(IOException ex){
                        }
                    }
                    try{
                        if(scan!=null && canContinue){
                            int pastNumber = scan.nextInt();
                            String pastAuthor = null;
                            try{
                                pastAuthor = scan.next();
                            }
                            catch(NoSuchElementException exe){
                                if(pastNumber+1 != intResult){
                                    // Wrong answer
                                    PrintWriter out = null;
                                    try{
                                        out = new PrintWriter(new File("count.count"));
                                        out.write("0");
                                        out.close();
                                    }
                                    catch(IOException ex){}
                                    event.getMessage().addReaction("❌").queue();
                                    EmbedBuilder embed = new EmbedBuilder();
                                    embed.setTitle("Count has been reset to 0. Start at 1");
                                    embed.appendDescription(event.getAuthor().getAsMention() + " has messed up the count at **" + pastNumber +"**. Try again!");
                                    event.getChannel().sendMessage(embed.build()).reference(event.getMessage()).queue();
                                }
                                else{
                                    // Right answer
                                    PrintWriter out = null;
                                    try{
                                        out = new PrintWriter(new File("count.count"));
                                        out.write(""+intResult + " " + event.getAuthor().getId());
                                        out.close();
                                        event.getMessage().addReaction("✅").queue();
                                    }
                                    catch(IOException ex){}
                                }
                            }
                            if(pastAuthor!=null){
                                if(pastNumber+1 != intResult || pastAuthor.equals(event.getAuthor().getId())){
                                    // Wrong answer
                                    PrintWriter out = null;
                                    try{
                                        out = new PrintWriter(new File("count.count"));
                                        out.write("0");
                                        out.close();
                                    }
                                    catch(IOException ex){}
                                    event.getMessage().addReaction("❌").queue();
                                    EmbedBuilder embed = new EmbedBuilder();
                                    embed.setTitle("Count has been reset to 0. Start at 1");
                                    embed.appendDescription(event.getAuthor().getAsMention() + " has messed up the count at **" + pastNumber +"**. Try again!");
                                    event.getChannel().sendMessage(embed.build()).reference(event.getMessage()).queue();
                                }
                                else{
                                    // Right answer
                                    PrintWriter out = null;
                                    try{
                                        out = new PrintWriter(new File("count.count"));
                                        out.write(""+intResult + " " + event.getAuthor().getId());
                                        out.close();
                                        event.getMessage().addReaction("✅").queue();
                                    }
                                    catch(IOException ex){}
                                }
                            }
                        }
                    }
                    catch(NoSuchElementException e){
                    }
                }
                else{}
            }
            else{}
        }
    }
    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        if(event.getAuthor().getId().equals("540957458944884746")){
            String[] args = event.getMessage().getContentRaw().split("\\s+");
            if(args[0].equalsIgnoreCase(ProbieBot.prefix + "message")){
                // Given a channel, it will send a message
                String channelid = null;
                try{
                    if(event.getMessage().getReferencedMessage()!=null){
                        String[] argsref = event.getMessage().getReferencedMessage().getContentRaw().split("\\s+");
                        channelid = argsref[0];
                    }
                    else{
                        channelid = args[1];
                    }
                }
                catch (ArrayIndexOutOfBoundsException ex){
                    event.getMessage().addReaction("❌").queue();
                }
                if (channelid != null){
                    TextChannel channel = null;
                    try{
                        channel = event.getJDA().getTextChannelById(channelid);
                    }
                    catch (Exception ex){
                        event.getMessage().addReaction("❌").queue();
                    }
                    if(channel != null){
                        StringBuilder message = new StringBuilder();
                        if(event.getMessage().getReferencedMessage()!=null){
                            for (int i = 1; i < args.length; i++) {
                                message.append(args[i]);
                                if(i!=args.length-1){
                                    message.append(" ");
                                }
                            }
                            if(message.toString().length()!=0){
                                channel.sendMessage(message.toString()).queue();
                            }
                        }
                        else{
                            for (int i = 2; i < args.length; i++) {
                                message.append(args[i]);
                                if(i!=args.length-1){
                                    message.append(" ");
                                }
                            }
                            if(message.toString().length()!=0){
                                channel.sendMessage(message.toString()).queue();
                            }
                        }
                    }
                }
            }
            else if(args[0].equalsIgnoreCase(ProbieBot.prefix + "reply")){
                // Given a channelid and messageid, it will reply
                String channelid = null;
                String messageid = null;
                try{
                    if(event.getMessage().getReferencedMessage()!=null){
                        String[] argsref = event.getMessage().getReferencedMessage().getContentRaw().split("\\s+");
                        channelid = argsref[0];
                        messageid = argsref[1];
                    }
                    else{
                        channelid = args[1];
                        messageid = args[2];
                    }
                }
                catch (ArrayIndexOutOfBoundsException ex){
                    event.getMessage().addReaction("❌").queue();
                }
                if (channelid != null){
                    TextChannel channel = null;
                    try{
                        channel = event.getJDA().getTextChannelById(channelid);
                    }
                    catch (Exception ex){
                        event.getMessage().addReaction("❌").queue();
                    }
                    if(channel != null){
                        StringBuilder message = new StringBuilder();
                        if(event.getMessage().getReferencedMessage()!=null){
                            for (int i = 1; i < args.length; i++) {
                                message.append(args[i]);
                                if(i!=args.length-1){
                                    message.append(" ");
                                }
                            }
                            if(message.toString().length()!=0){
                                channel.sendMessage(message.toString()).referenceById(messageid).queue();
                            }
                        }
                        else{
                            for (int i = 3; i < args.length; i++) {
                                message.append(args[i]);
                                if(i!=args.length-1){
                                    message.append(" ");
                                }
                            }
                            if(message.toString().length()!=0){
                                channel.sendMessage(message.toString()).referenceById(messageid).queue();
                            }
                        }
                    }
                }
            }
            else if(args[0].equalsIgnoreCase(ProbieBot.prefix + "react")){
                // Given a channel id and messageid, it will react
                String channelid = null;
                String messageid = null;
                try{
                    if(event.getMessage().getReferencedMessage()!=null){
                        String[] argsref = event.getMessage().getReferencedMessage().getContentRaw().split("\\s+");
                        channelid = argsref[0];
                        messageid = argsref[1];
                    }
                    else{
                        channelid = args[1];
                        messageid = args[2];
                    }
                }
                catch (ArrayIndexOutOfBoundsException ex){
                    event.getMessage().addReaction("❌").queue();
                }
                if (channelid != null){
                    TextChannel channel = null;
                    try{
                        channel = event.getJDA().getTextChannelById(channelid);
                    }
                    catch (Exception ex){
                        event.getMessage().addReaction("❌").queue();
                    }
                    if(channel != null){
                        Emote emoji = null;
                        try{
                            emoji = event.getMessage().getEmotes().get(0);
                        }
                        catch(Exception ex){
                            event.getMessage().addReaction("❌").queue();
                        }
                        if(emoji!=null){
                            channel.addReactionById(messageid, emoji).queue();
                        }
                    }
                }
            }
        }
    }
    public String embedToString(List<MessageEmbed> list){
        List<Field> fields = null;
        ArrayList<String> titleDesc = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            fields = list.get(i).getFields();
            if(list.get(i).getDescription()!=null){
                titleDesc.add(list.get(i).getDescription());
            }
            if(list.get(i).getTitle()!=null){
                titleDesc.add(list.get(i).getTitle());
            }
        }
        for (int i = 0; i < titleDesc.size(); i++) {
            sb.append(titleDesc.get(i));
            sb.append(" ");
        }
        if(fields == null){}
        else{
            for (int i = 0; i < fields.size(); i++) {
                 sb.append(fields.get(i).getName());
                 sb.append(" ");
                 sb.append(fields.get(i).getValue());
                 sb.append(" ");
            }
        }
        return sb.toString();
    }
    private String extractTitle(String content) {
        final Document doc = Jsoup.parse(content);
        final Element titleElement = doc.select("title").first();
        return titleElement.text();
    }
    private EmbedBuilder weatherScrape(String URL) {
      int temperature = 0;
      String cloudyness = null;
      int dewPoint = 0;
      int humidity = 0;
      String citystate = null;
      int highTemp = 0;
      int lowTemp = 0;
      String highTempString = null;
      String lowTempString = null;
      final String httpsUrl = URL;
      try {
        final URL url = new URL(httpsUrl);
         URLConnection con = new URL(URL).openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        con.connect();
         PrintWriter toFile = new PrintWriter(new File("test.test"));
         final BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                
         String input;
         StringBuilder sb = new StringBuilder();
         while ((input = br.readLine()) != null){
           sb.append(input);
           sb.append("\n");
         }
          toFile.append(sb);
          toFile.close();
         br.close();
         
         
         Scanner fromFile = new Scanner(new File("test.test"));
         
         while(fromFile.hasNext()){
             String data = null;
             data = fromFile.next();
             // Scan for temperature
            if(data.equals("Weather")){
                if(fromFile.next().equals("Conditions</span><span")){
                    for (int i = 0; i < 28; i++) {
                        fromFile.next();
                    }
                    String temp = fromFile.next();
                    temperature = Integer.parseInt(temp.substring(temp.indexOf("\">")+2, temp.indexOf("</")));
                }
            }
            // Scan for high temperature (left)
            if(data.contains("condition-data")){
                fromFile.next();
                if(fromFile.next().contains("hi-lo")){
                    fromFile.next();
                    String temp = fromFile.next();
                    if(temp.contains("hi")){
                        temp = temp.substring(temp.indexOf("\"hi\">")+5,temp.indexOf("°<"));
                        try{
                            highTemp = Integer.parseInt(temp);
                        }
                        catch(NumberFormatException e){
                            highTempString = temp;
                        }
                        for (int i = 0; i < 5; i++) {
                            fromFile.next();
                        }
                        temp = fromFile.next();
                        temp = temp.substring(temp.indexOf("\"lo\">")+5, temp.indexOf("°<"));
                        try{
                            lowTemp = Integer.parseInt(temp);
                        }
                        catch(NumberFormatException e){
                            lowTempString = temp;
                        }
                    }
                }
            }
            // Scan for low temperature (right)
            // Get new City, ST
            if(data.contains("<title>")){
                StringBuilder sbd = new StringBuilder();
                sbd.append(data);
                sbd.append(" ");
                String temp = data;
                while(!(temp = fromFile.next()).equals("Weather")){
                    sbd.append(temp);
                    sbd.append(" ");
                }
                for (int i = 0; i < 7; i++) {
                    sbd.deleteCharAt(0);
                }
                citystate = sbd.toString();
            }
             
            // Scan for cloudyness
             else if(data.contains("Clouds")){
                 fromFile.next();
                 if(fromFile.next().equals("class=\"small-8")){
                     for (int i = 0; i < 2; i++) {
                         fromFile.next();
                     }
                     StringBuilder sbd = new StringBuilder();
                     String temp = fromFile.next();
                     if(temp.contains("</span")){
                         sbd.append(temp.substring(temp.indexOf(">")+1,temp.indexOf("</span")));
                     }
                     else{
                        sbd.append(temp.substring(temp.indexOf(">")+1));
                     }
                     sbd.append(" ");
                     String temp2 = fromFile.next();
                     if(temp2.contains("</span")){
                         sbd.append(temp2.substring(0,temp2.indexOf("</span")));
                        sbd.append(" ");
                     }
                     else if(!temp2.contains("_ngcontent")){
                         sbd.append(temp2);
                        sbd.append(" ");
                     }
                     String temp3 = fromFile.next();
                     if(temp3.contains("</span")){
                         sbd.append(temp3.substring(0,temp3.indexOf("</span")));
                        sbd.append(" ");
                     }
                     cloudyness=sbd.toString().substring(0,sbd.length()-1);
                 }
                 
             }
             // Scan for Dew Point
             else if(data.contains("columns\">Dew")){
                 if(fromFile.next().equals("Point</div><div")){
                     for (int i = 0; i < 14; i++) {
                         fromFile.next();
                     }
                     String temp = fromFile.next();
                     temp = temp.substring(temp.indexOf(">")+1,temp.indexOf("</span"));
                     dewPoint = Integer.parseInt(temp);
                 }
             }
             // Scan for Relative Humidity
             else if(data.contains("Humidity")){
                 fromFile.next();
                 if(fromFile.next().equals("class=\"small-8")){
                     for (int i = 0; i < 12; i++) {
                         fromFile.next();
                     }
                     String temp = fromFile.next();
                     temp = temp.substring(temp.indexOf(">")+1,temp.indexOf("</span"));
                     humidity = Integer.parseInt(temp);
                 }
             }
             
         }
      } catch (MalformedURLException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      EmbedBuilder eb = new EmbedBuilder();
      eb.setTitle("In " +citystate+":");
      eb.setDescription("Temperature: "+temperature+"°F\n"
              +         "High/Low: " +(highTempString==null?highTemp:highTempString) + "°F/" + (lowTempString==null?lowTemp:lowTempString) + "°F\n"
              +         "Dew Point: "+dewPoint+"°F\n"
              +         "Relative Humidity: "+humidity+"%\n"
              +         "Cloudiness: "+cloudyness);
      eb.setColor(Color.GREEN);
      //(new File("test.test")).delete();
      return eb;
   }
    private EmbedBuilder gasScrape(String URL){
        String citystate = null;
        ArrayList<GasPrice> tempStations = new ArrayList<>();
          ArrayList<GasPrice> stations = new ArrayList<>();
          ArrayList<String> names = new ArrayList<>();
          ArrayList<String> addresses = new ArrayList<>();
          ArrayList<Double> prices = new ArrayList<>();
        try{
        final String httpsUrl = URL;
        final URL url = new URL(httpsUrl);
         URLConnection con = new URL(URL).openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        con.connect();
         PrintWriter toFile = new PrintWriter(new File("gas.gas"));
         final BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                
         String input;
         StringBuilder sb = new StringBuilder();
         while ((input = br.readLine()) != null){
           sb.append(input);
           sb.append("\n");
         }
          toFile.append(sb);
          toFile.close();
         br.close();
         Scanner fromFile = new Scanner(new File("gas.gas"));
         
          while(fromFile.hasNext()){
             String data = null;
             data = fromFile.next();
             // Get city and state
             if(data.contains("SearchResultsPage-module__summary")){
                 for (int i = 0; i < 13; i++) {
                     fromFile.next();
                 }
                 if(fromFile.next().contains("SearchResultsPage-module__resultsHeader")){
                     for (int i = 0; i < 3; i++) {
                         fromFile.next();
                     }
                     StringBuilder sbd = new StringBuilder();
                     sbd.append(fromFile.next());
                    sbd.append(" ");
                     String temp = null;
                     while(!(temp = fromFile.next()).contains("</h3>")){
                        sbd.append(temp);
                        sbd.append(" ");
                    }
                    sbd.append(temp.substring(0,temp.indexOf("</h3>")));
                    citystate = sbd.toString();
                 }
             }
             // Search for gas prices
             else if(data.contains("StationDisplay-module__stationNameHeader")){
                 fromFile.next();
                 StringBuilder sbd = new StringBuilder();
                 String temp = fromFile.next();
                 sbd.append(temp.substring(temp.indexOf("\">")+2));
                 sbd.append(" ");
                 if(!sbd.toString().contains("</a>")){
                    while(!(temp = fromFile.next()).contains("</a>")){
                        sbd.append(temp);
                        sbd.append(" ");
                    }
                 }
                 sbd.append(temp);
                 names.add(sbd.toString().substring(0,sbd.toString().indexOf("</a>")).replace("&#x27;", "'").replace("&amp;", "&"));
                 while(!(temp = fromFile.next()).contains("module__address___2")){}
                 sbd = new StringBuilder();
                 sbd.append(temp);
                 sbd.append(" ");
                 while(!(temp = fromFile.next()).contains("</div>")){
                     sbd.append(temp);
                     sbd.append(" ");
                 }
                 sbd.append(temp);
                 temp = sbd.toString();
                 temp = temp.substring(temp.indexOf("\">")+2,temp.indexOf("</div>"));
                 temp = temp.replace("<br/>", ", ");
                 temp = temp.replace("&#x27;", "'").replace("&amp;", "&");
                 addresses.add(temp);
                 while(!(temp = fromFile.next()).contains("StationDisplayPrice-module__price")){}
                 while(!(temp = fromFile.next()).contains("StationDisplayPrice-module__price")){}
                 if(temp.contains("</span>")){
                    temp = temp.substring(temp.indexOf("\">$")+3,temp.indexOf("</span>"));
                 }
                 else{
                     temp = "null";
                 }
                try{
                     prices.add(Double.parseDouble(temp));
                 }
                 catch(NumberFormatException e){
                     prices.add(0.0);
                 }
             }
          }
          for (int i = 0; i < prices.size(); i++) {
        try{
           tempStations.add(new GasPrice(names.get(i), addresses.get(i), prices.get(i)));
        }
        catch(IllegalArgumentException e){}
        }
        double[] tempArray = new double[tempStations.size()];
        for (int i = 0; i <tempStations.size(); i++) {
            tempArray[i] = tempStations.get(i).getPrice();
        }
        Arrays.sort(tempArray);
        for (int i = 0; i < tempArray.length; i++) {
            for (int j = 0; j < tempStations.size(); j++) {
                if(tempArray[i] == tempStations.get(j).getPrice()){
                    stations.add(tempStations.get(j));
                    tempStations.remove(j);
                }
            }
        }
        }
        catch (Exception e){
        }
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Gas Prices in " + citystate + ":");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stations.size(); i++) {
            String temp = ""+stations.get(i).getPrice();
            if(temp.length()==3){
                sb.append((i+1)+") "+stations.get(i).getName()+", "+stations.get(i).getAddress()+": $"+stations.get(i).getPrice()+"0\n");
            }
            else{
                sb.append((i+1)+") "+stations.get(i).getName()+", "+stations.get(i).getAddress()+": $"+stations.get(i).getPrice()+"\n");
            }
        }
        embed.setDescription(sb.toString());
        embed.setColor(Color.GREEN);
        //new File("gas.gas").delete();
            
        return embed;
    }
    private String puppyScrape(String URL){
        try{
        final String httpsUrl = URL;
        final URL url = new URL(httpsUrl);
         URLConnection con = new URL(URL).openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        con.connect();
         PrintWriter toFile = new PrintWriter(new File("puppy.puppy"));
         final BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                
         String input;
         StringBuilder sb = new StringBuilder();
         while ((input = br.readLine()) != null){
           sb.append(input);
           sb.append("\n");
         }
          toFile.append(sb);
          toFile.close();
         br.close();
         
         
         Scanner fromFile = new Scanner(new File("puppy.puppy"));
         String temp = null;
          while(fromFile.hasNext()){
              if(fromFile.next().contains("thumbnail-col-1")){
                  for (int i = 0; i < 5; i++) {
                      fromFile.next();
                  }
                  temp = fromFile.next();
                  temp = temp.substring(temp.indexOf("\"")+1,temp.lastIndexOf("\""));
                  temp = "https://www.generatormix.com/" + temp;
              }
          }
         //(new File("puppy.puppy")).delete();
          return temp;
        }
        catch(Exception ex){
            return null;
        }
        
    }
    private EmbedBuilder anagramScrape(String URL, String userIn){
        try{
        final String httpsUrl = URL;
        final URL url = new URL(httpsUrl);
         URLConnection con = new URL(URL).openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        con.connect();
         PrintWriter toFile = new PrintWriter(new File("anagram.anagram"));
         final BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                
         String input;
         StringBuilder sb = new StringBuilder();
         while ((input = br.readLine()) != null){
           sb.append(input);
           sb.append("\n");
         }
          toFile.append(sb);
          toFile.close();
         br.close();
         
         
         Scanner fromFile = new Scanner(new File("anagram.anagram"));
         String[] anagrams = new String[10];
          while(fromFile.hasNext()){
              if(fromFile.next().contains("document.body.style.cursor='wait';")){
                  for (int i = 0; i < 3; i++) {
                      fromFile.next();
                  }
                  String data = null;
                  StringBuilder sbd = new StringBuilder();
                  int index = 0;
                  while(!(data = fromFile.next()).contains("<script>") && anagrams[9]==null){
                      if(data.contains("</b>")){
                          String temp;
                          temp = data.substring(data.indexOf("</b><br>")+8);
                          if(temp.contains("<br>")){
                              sbd.append(temp.substring(0,temp.indexOf("<br>")));
                              anagrams[index++] = sbd.toString();
                              sbd = new StringBuilder();
                          }
                          else{
                            sbd.append(temp);
                            sbd.append(" ");
                          }
                      }
                      else{
                          if(data.contains("<br>")){
                              sbd.append(data.substring(0,data.indexOf("<br>")));
                              anagrams[index++] = sbd.toString();
                              sbd = new StringBuilder();
                          }
                          else{
                            sbd.append(data);
                            sbd.append(" ");
                          }
                      }
                  }
              }
          }
          EmbedBuilder embed = new EmbedBuilder();
          embed.setTitle("Anagrams for " + userIn);
            for (int i = 0; i < 10; i++) {
                if(anagrams[i]!=null){
                    embed.appendDescription(i+1+". "+anagrams[i]+"\n");
                }
            }
            embed.setColor(Color.GREEN);
          (new File("anagram.anagram")).delete();
          return embed;
        }
        catch(Exception ex){
            return null;
        }
        
    }
    private String flmanScrape(String URL){
        try{
        final String httpsUrl = URL;
        final URL url = new URL(httpsUrl);
         URLConnection con = new URL(URL).openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        con.connect();
         PrintWriter toFile = new PrintWriter(new File("flman.flman"));
         final BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
         String input;
         StringBuilder sb = new StringBuilder();
         while ((input = br.readLine()) != null){
           sb.append(input);
           sb.append("\n");
         }
          toFile.append(sb);
          toFile.close();
         br.close();
         Scanner fromFile = new Scanner(new File("flman.flman"));
         String temp = null;
         String link = null;
          while(fromFile.hasNext() && link==null){
              temp = fromFile.next();
              if(temp.contains("url=http")){
                  link = temp.substring(temp.indexOf("url=")+4,temp.lastIndexOf("&amp;ved"));
              }
          }
        (new File("flman.flman")).delete();
          return link;
        }
        catch(Exception ex){
            return null;
        }
    }
    private String radarScrape(String URL){
        try{
        final String httpsUrl = URL;
        final URL url = new URL(httpsUrl);
         URLConnection con = new URL(URL).openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        con.connect();
         PrintWriter toFile = new PrintWriter(new File("radar.radar"));
         final BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
         String input;
         StringBuilder sb = new StringBuilder();
         while ((input = br.readLine()) != null){
           sb.append(input);
           sb.append("\n");
         }
          toFile.append(sb);
          toFile.close();
         br.close();
         Scanner fromFile = new Scanner(new File("radar.radar"));
         String temp = null;
         String link = null;
          while(fromFile.hasNext() && link==null){
              temp = fromFile.next();
              if(temp.contains("Radar")){
                  fromFile.next();
                  temp = fromFile.next();
                  if(temp.contains("Satellite")){
                      temp = fromFile.next();
                      if(temp.contains("</h4>")){
                          fromFile.next();
                          temp = fromFile.next();
                          link = temp.substring(temp.indexOf("station/")+8,temp.indexOf("/standard"));
                      }
                  }
              }
          }
          
        (new File("radar.radar")).delete();
        link = "https://radar.weather.gov/ridge/standard/"+link.toUpperCase()+"_loop.gif";
          return link;
        }
        catch(Exception ex){
            return null;
        }
    }
    private String latLongScrape(String zip){
        String latlon = null;
        Scanner scan = null;
        try{
            scan = new Scanner(new File("zipcodes.code"));
        }
        catch (FileNotFoundException ex){}
        while(scan.hasNext() && latlon == null){
            String temp = scan.nextLine();
            if(temp.contains(zip)){
                latlon = temp;
            }
        }
        latlon = latlon.substring(latlon.indexOf(",")+1);
        
        return latlon;
    }
}
