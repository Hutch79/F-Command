# F-Command
![bStats Servers](https://img.shields.io/bstats/servers/17738?style=for-the-badge)
![bStats Players](https://img.shields.io/bstats/players/17738?style=for-the-badge)
![GitHub last commit](https://img.shields.io/github/last-commit/Hutch79/F-Command?style=for-the-badge)
![Spiget Download Size](https://img.shields.io/spiget/download-size/108009?style=for-the-badge)
![Spiget Downloads](https://img.shields.io/spiget/downloads/108009?style=for-the-badge)
![Spiget Rating](https://img.shields.io/spiget/rating/108009?style=for-the-badge)  
---
# Description
F-Command is a little and easy to configure Minecraft Plugin which lets you set up multiple commands which are executet on pressing the F key.  
You can add a near unlimited amount of Commands which can be configured induvidualy to your needs!
For example with different Permissions per command or if your Players need to sneak or not.

# Config
``` yaml
command:
  # Name of the Command.
  saySomething:
    # Does the Player need to sneak to execute the command?
    # True/False
    requireShift: True
    # Which permission is required to execute this command?
    permission: fcommand.example
    # Which command should be executed?
    # Enter without /
    command: say hey
    # should the event be canceled?
    cancel: true
```

# bStats
[![](https://bstats.org/signatures/bukkit/F-Command.svg)](https://bstats.org/plugin/bukkit/F-Command)

## Special Thanks
[mfnalex](https://github.com/JEFF-Media-GbR/Spigot-UpdateChecker) for his Plugin Update Checker.
