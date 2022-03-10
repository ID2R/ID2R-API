# ID2R API
<div>
  <a href="https://search.maven.org/search?q=g:%22dev.id2r%22%20AND%20a:%22api-common%22"> <img src="https://img.shields.io/maven-central/v/dev.id2r/api-common.svg?label=Maven%20Central" alt="maven central" /></a>
  <a href="https://discord.gg/RZsse9tSWD"><img src="https://img.shields.io/discord/861156260422221824?logo=discord" alt="discord"></a>
  <a href="https://github.com/id2r/id2r-api/graphs/contributors" alt="Contributors"> <img src="https://img.shields.io/github/contributors/id2r/id2r-api" /></a>
</div>

<br>
Public API for all of ID2R's projects. This API contains some useful utilities and tools that will help ID2R to deliver high quality code. originally developed by Invvk.

## Features
Some of the features that this library delivers:
 - Support Velocity, Bungeecord, spigot (preferably **Paper**), and any fork of the mentioned platforms.
 - Fully shadable, no need for it to be a seperate plugin.
 - Dependency API
   - Injects libraries at runtime.
   - Eliminates the need for shading libraries that results in big jars (5MB+).
   - Downloads required dependencies at runtime.
   - Does a SHA256 check at runtime to protect against possible imposter jars.
   - Has the ability to do a relocation at runtime (thanks to lucko's library [jar-relocator](https://github.com/lucko/jar-relocator)).
   - Isolated class loader support.
   - Downloads from any repository (maven, sonatype, jitpack, etc..)
     - _you can add your custom own repository_
 - Placeholder System (inspired from [PlaceholderAPI](https://github.com/PlaceholderAPI/PlaceholderAPI)).
 - Gson execlusion strategy.
 - Boilerplate code for all of the supported platforms.
 - Task Manager.
 - Log Manager (_log4j, jtuil, SLF4J_).
 - ItemStack API (_Spigot_).
 - Version API (_Spigot_).
 
## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.
 - Make sure you are following our code style.
 - Make sure to test the changes before opening a pull request.

## License
This project falls under the [MIT](https://choosealicense.com/licenses/mit/) license.
