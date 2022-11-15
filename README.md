# ProbieBot
## Summary

ProbieBot is a Discord bot that was created in the summer of 2022. It is an ongoing project that started out as a small bot that could save quotes from people, but quickly turned into a powerful bot with many features. There is no current end to this project, and will continue to be added onto until the server that it is on is satisfied, or the owner is satisfied.

## Objective

The objective of ProbieBot was to make it easier for people to use Discord with certain features and be able to use automation to be able to interact with the real world. 

## Features

There are many features in this bot, and this list will continue to grow. Here is the list of features and their description:

- %info
    - Gives a general description of ProbieBot
- %help
    - Gives a categorization of all commands available.
- %roast
    - Will roast the person with a set list of roasts defined by the owner.
- %whois
    - Gives a description of the person pinged from a set of user-defined descriptions.
- %addwhois
    - Adds description to yourself
- %editwhois
    - Replaces your current description with the description set by this command.
- %deletewhois
    - Deletes your description from the list of descriptions.
- %addquote
    - When replied to a message with this command, it will save the contents of the message along with the sender and quoter.
- %deletequote
    - This will delete a quote (can only be done by the quoter or the quotee)
- %quotes
    - When used with a ping of someone, it will return all quotes said by this person.
- %viewquotes
    - This will view all quotes between a set of quote numbers
- %tarotqueue
    - This is a command that is used during tarot card readings, that will show the queue of people.
- %addtarotqueue
    - This will add yourself to the tarot queue.
- %deletetarotqueue
    - This will remove yourself from the tarot queue, if you are in queue.
- %nexttarotqueue
    - This is reserved for the tarot reader, it pushes the next person off the stack and returns the next person in the queue.
- %weather
    - This will give the weather in a specified City and state. Currently it only works in the US. This uses Java web scraping, as opposed to an API.
- %gas
    - This will give a list of the cheapest gas prices specified by City and state. Currently only works in the US for regular gas. This uses Java web scraping, as opposed to an API.
- %suggest
    - This is a suggestion box for the users of the server. It will send a direct message to the owner on suggestions for the bot.
- %addbday
    - This adds a date for your birthday, which is announced in the #commons channel.
- %anagram
    - When sent with a message, it will send a list of possible anagrams specified by the message. This uses Java web scraping as opposed to a built-in program used to create anagrams.
- %flman
    - When sent with a date, it will send a Florida man article with that specified date.
    - When sent without a date, it will send the most recent Florida man article that can be found.
- %puppyme
    - Returns a picture of a puppy, via Java web scraping.
- %radar
    - If given no arguments, it will return a radar image of CONUS.
    - If given a zip code, it will return a radar image of the area around that zip code.
- %latex
    - If given no arguments, it will return a PNG image (1000 dpi) of the LaTeX expression in white text
    - If given color as an argument, it will return a PNG image (1000 dpi) of the LaTeX expression in that color.

## Limitations

There are many limitations to this bot, as it is using Discord messages to interact with different features. The majority of them are based on incorrect inputs. When given an incorrect input, it will either throw an exception (which will be recorded via Discord message), or the bot will react with an "X", which indicates a wrong input has been made.

## Why web scraping??

The reason that web scraping was used in most cases was to familiarize myself with the ups and downs of working with HTML in Java. Additionally, there are no API's that will return a list of gas prices or puppies, so I was forced to use a different method to achieve the results necessary.

## Libraries

In this project, two main libaries have been used:  
[JDA Discord - 4.4.0_352](https://github.com/DV8FromTheWorld/JDA) - This allows the program to interact with the Discord API and controls the message interface of the bot and the server. This is the main package used in this project.  

[JSoup-1.15.2](https://jsoup.org/) - This library allows Java to parse web pages in HTML and is used in web scraping.  

[All Zip Codes With Their Corresponding Latitude and Longitude Coordinates](https://gist.github.com/erichurst/7882666) - This file allows for the %radar command to work properly. This file is slightly edited to work with the program properly.
