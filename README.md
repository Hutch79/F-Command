![bStats Servers](https://img.shields.io/bstats/servers/17738?style=for-the-badge) 
![bStats Players](https://img.shields.io/bstats/players/17738?style=for-the-badge) 
![GitHub last commit](https://img.shields.io/github/last-commit/Hutch79/F-Command?style=for-the-badge) 
![Spiget Downloads](https://img.shields.io/spiget/downloads/108009?style=for-the-badge) 
![Spiget Rating](https://img.shields.io/spiget/rating/108009?style=for-the-badge)

[![Discord](https://github-production-user-asset-6210df.s3.amazonaws.com/42042811/249748477-12729e90-064e-4647-93cb-faae60cb033e.png)](https://dc.hutch79.ch)

# Description

F-Command is a little and easy to configure Minecraft Plugin which lets you set up multiple commands which are executed on pressing the F or Q key (Swap Hands or dropping Items).

You can configure every command exactly to your needs with the following easy to use options.

- Which Key should be pressed
- Per command Permission
- Define per command if it should be executed by the Player or the Server
- Should the action (Dropping item or swapping hand) be executed
- Does the Player need to sneak or not?

And the best thing is: You can even use PlaceholderAPI within the commands, and therefore integrate hundreds of other Plugins!

Every one of thees options is available for every command you configure!

# Usage Ideas

<details id="bkmrk-click-me-server-menu"><summary>Click me</summary>

## Server Menu

You have a menu where your players can easily switch servers on your network?  
Make it easily accessible by pressing shift+F to open the menu!

## Gui based AdminShop

You have a AdminShop in a GUI like EconomyShopGUI?  
Why not open it by pressing F?  
It's much easier than typing in a command!

</details>

# Commands &amp; Permision

Only one command, only one Permission!

`/fcmd reload` - Reloading your Config  
`fcommand.admin` - Update notification and access to /fcmd reload

# Config

```yaml
# Only turn this on if you're asked by the Dev
# Might spam your console and/or chat ;-)
debug: false

# Example configuration
command:
  # Name of the Command.
  command1:
    key: f  # f or q
    # Does the Player need to sneak to execute the command?
    requireShift: false
    # Which permission is required to execute this command?
    permission: f-command.example
    # Which command should be executed as the player?
    # You can also use PlaceholderAPI here!
    # Enter without /
    command: say f no shift
    # Stopping item drop or hand swap from happening
    cancel: true
    # Should the command be executed from the Server?
    executeAsServer: false

  command2:
    key: F
    requireShift: True
    permission: f-command.example
    command: say F shift
    cancel: true
    executeAsServer: false
```

# bStats

[![](https://bstats.org/signatures/bukkit/F-Command.svg)](https://bstats.org/plugin/bukkit/F-Command)

## Special Thanks

[mfnalex](https://github.com/JEFF-Media-GbR/Spigot-UpdateChecker) for his Plugin Update Checker  
[Ntdi ](https://github.com/n-tdi)for helping in development
