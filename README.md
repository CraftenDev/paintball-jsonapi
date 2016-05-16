# paintball-jsonapi #

This plugin adds some methods for [JSONAPI](http://mcjsonapi.com/) to access [Paintball War Edition](http://dev.bukkit.org/bukkit-plugins/paintball_pure_war/) players. It also supports a (cached) ranking by points.

## Requirements and Installation ##

This plugin depends on JSONAPI. It oviously also requires Paintball War Edition to work. Install all these plugins and that's it. No configuration needed.

## JSONAPI methods ##

While this plugins doesn't add any commands at all, it adds the following new JSONAPI methods:

| Name                 | Parameters           | Description                                                                 |
|----------------------|----------------------|-----------------------------------------------------------------------------|
| paintball.player     | playername           | Gets all information about the given player.                                |
| paintball.ranking    | offset, count        | Gets the given number of entries from the rankings with the given offset.   |
