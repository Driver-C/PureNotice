# PureNotice
#### About:
PureNotice is a pure and simple automessage plugin.It is used to automatically send announcement notice in the server.

Thanks for the configuration file logic provided by **pixelautomessages**.

#### Usage:
Put the jar into your mods/plugins/ and start your server.Then pray it will be running. :D

#### Command:
- **pn** Show the PureNotice's infomation.
- **pn reload** Reload the PureNotice.

#### Permission:
- **pn.command.client.main** For command "pn".
- **pn.command.admin.reload** For command "pn reload".

#### Function:
- Click notice to execute command.
- Shift + click notice to insert command or message into chat box.
- Click notice to open a url.
- Move the cursor over the notice to display additional information.
- Switch language to English or Chinese or use your own language configuration file.
- Random messages.
- The first notice can be delayed.

#### Effect display:
##### Click to execute command
![pn_help_en.png](https://i.loli.net/2020/06/21/Ro7kKqS4ZrvQL9e.png)

##### Shift + click to insert message or command into chat box.
![pn_list_en.png](https://i.loli.net/2020/06/21/2ky3RIV1EKxuroY.png)

##### Click to open url
![pn_url1_en.png](https://i.loli.net/2020/06/21/fBb8USJKa4vG1pL.png)
![pn_url2_en.png](https://i.loli.net/2020/06/21/pLRZUalkxDrWBQX.png)

##### When both click and clickUrl exists
![pn_all_en.png](https://i.loli.net/2020/06/21/Nf21AnOYDsdmwbo.png)

##### Commands
![pn_pn_en.png](https://i.loli.net/2020/06/21/9RQ62k83plZ7SYw.png)
![pn_reload_en.png](https://i.loli.net/2020/06/21/I1viNd9ucgmhLKA.png)

#### configuration:
##### main:
```
main {
    firstDelay=20
    interval=60
    # en_US or zh_CN
    language="en_US"
    prefix="&e[&a&lPureNotice&e]&r"
    random=false
    # Do not change version!!!
    version="1.0"
}
messages {
    "1" {
        message="&fThanks for using &aPureNotice&f, this is a normal message with no function."
    }
    "2" {
        click="/help"
        hover="&eClick&f to execute &a/help"
        message="&fThanks for using &aPureNotice&f, this message is used to show the &eclick&f to execute command function."
    }
    "3" {
        hover="&eShift + click&f to insert &a/list&f to the chat box"
        message="&fThanks for using &aPureNotice&f, this message is used to show the &eshift + click&f to insert a message or command into the chat box function."
        shiftInsert="/list"
    }
    "4" {
        clickUrl="https://www.google.com/"
        hover="&eClick&f to open &ehttps://www.google.com/"
        message="&fThanks for using &aPureNotice&f, this message is used to show the &eclick&f to open a url function."
    }
    "5" {
        click="/help"
        clickUrl="https://www.google.com/"
        hover="When both &eclick&f and &eclickUrl&f exist, only &cclick&f will take effect."
        # When both click and clickUrl exist, only click will take effect.
        message="&fThanks for using &aPureNotice&f, this message is used to show &eall functions"
        shiftInsert="/list"
    }
    "10" {
        message="&fThanks for using &aPureNotice&f, This message is used to show that the &emessage index&f does not need to be incremented by 1 at a time."
    }
}
```

##### language:
```
main {
    # Do not change version!!!
    version="1.0"
}
messages {
    commandMain="§aPureNotice§r version:"
    commandMainAuthors="§aPureNotice§r authors: §a§l"
    commandMainDesc="Main."
    commandMainHelp="§epurenotice(pn) reload §fReload PureNotice"
    commandReloadDesc="Reload this plugin."
    configOut="Config is out of date, config has been overwritten and the older has been saved to the config directory."
    disabled="§aPureNotice disabled.§r"
    firstRun="This may be the first running. Config initialized."
    languageOverWritten="Language config is out of date, it has been overwritten."
    loaded="§aPureNotice loaded.§r"
    loading="§aPureNotice is loading.§r"
    noInit="Config already exists. No initialization required."
    reloaded="§aPureNotice reloaded.§r"
    reloading="§aPureNotice is reloading.§r"
}
```

**The main configuration file is used to configure messages and functions to be sent, and the language configuration file is used to configure the PureNotice's tips.**
