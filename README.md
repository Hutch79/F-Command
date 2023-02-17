# F-Command
![bStats Servers](https://img.shields.io/bstats/servers/17738?style=for-the-badge)
![bStats Players](https://img.shields.io/bstats/players/17738?style=for-the-badge)
![GitHub last commit](https://img.shields.io/github/last-commit/Hutch79/F-Command?style=for-the-badge)
![Spiget Download Size](https://img.shields.io/spiget/download-size/108009?style=for-the-badge)
![Spiget Downloads](https://img.shields.io/spiget/downloads/108009?style=for-the-badge)
![Spiget Rating](https://img.shields.io/spiget/rating/108009?style=for-the-badge)  
---
# Description
F-Command is a little and easy to configure Minecraft Plugin which lets you set up multiple commands which are executed on pressing the F key.  

You can configure every command exactly to your needs with four easy to use options.  
- Per command Permission  
- Define per command if it should be executed by the Player or the Server  
- Should the Offhand be switched or not?  
- Does the Player need to sneak or not?  

And the best thing is: You can even use PlaceholderAPI within the commands, and therefore integrate hundreds  of other Plugins!

Every one of thees options is available for every command you configure!

# Usage Ideas
<details>
  <summary>Click me</summary>
  
## Server Menu
You have a menu wher your players can easaly switch servers on your network?  
Make it easaly accasible by pressing shift+F to open the menu!

## Gui based AdminShop
You have a AdminShop in a GUI like EconomyShopGUI?  
Why not open it by pressing F?  
Its much easier then typing in a command!

</details>

# Commands & Permision
Only one Command, only one Permission!

`/fcmd reload` - Reloading your Config  
`fcommand.admin` - Update notification and access to /fcmd reload  

# Config
``` yaml
# Example configuration
command:
  # Name of the Command.
  Command1:
    # Does the Player need to sneak to execute the command?
    # True/False
    requireShift: false
    # Which permission is required to execute this command?
    permission: f-command.example
    # Which command should be executed as the player?
    # You can also use PlaceholderAPI here!
    # Enter without /
    command: say hey
    # should the event be canceled?
    cancel: true
    # Should the command be executed from the Server?
    executeAsServer: false
    
  Command2:
    # Add more commands here!
```

# bStats
[![](https://bstats.org/signatures/bukkit/F-Command.svg)](https://bstats.org/plugin/bukkit/F-Command)

## Special Thanks
[mfnalex](https://github.com/JEFF-Media-GbR/Spigot-UpdateChecker) for his Plugin Update Checker.
