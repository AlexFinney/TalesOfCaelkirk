# TalesOfCaelkirk - A Minecraft MMORPG

- To setup a dev environment, download Minecraft Forge 1.13.2 (or a different version as appropriate) and extract.
- Clone this repo into your extracted forge source folder and execute "gradlew genEclipseRuns" in a command window.
- Next, run "gradlew eclipse" to generate an eclipse project.
- Open eclipse and import an existing project into the workspace.  Navigate to the forge source folder, and import Tales of Caelkirk
- To run, open the run configurations for the project, and run either "runClient" or "runServer"
- To set your minecraft username and pass, set them in the program arguments in the debugging settings with --username [user] and --paswword [pass]
