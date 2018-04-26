# Configuration

```
#               MULTI RESOURCE PACK v0.1 BETA CONFIG
# Setting use_force_timer
#   
#   force_timer is after player join and start counting until pending_time ( seconds ) reach 
#   and kick the player if haven't accept or choose  a resource pack.
# 
#  Setting use_gui & kick_when_close
#   
#   use_gui is after player join and show the resourcepack gui and player choose themselves
#   but if kick_when_close has enabled , player close inventory without choosing any resourcepack
#   will kick the player.
#
#  Setting use_default
#
#   use_default is after player join and ask user to accept the default resourcepack.
#
download:
    declined:    
    # Occurs when user declined for downloading resource pack
        kick: true
        message: '§cYou need to accept resourcepack!'
    failed_download:     
    # Occurs when failed downloaded resource pack
        kick: true
        message: '§fYou have failed download resourcepack.\n§fContact the Server Admin'
command:
    use: true
    permission: 'mrp.use'
login:
    event: 'default'      
    # Currently only support "default" & "authme"
    
    use_force_timer: true
    use_default: false
    use_gui: true
    timer:
        message: '§fAlready reach pending time ...' 
        pending_time: 10
    default:
        default_url: https://www.dropbox.com/s/j0oltf2zqaz942i/1.11.2.zip?dl=1
        default_hash: 391562CA4A83114577AECC4AC475EA6B2D16B6C9
        # You can get the hash via SHA1 ( URL = http://onlinemd5.com )
        # Open the link and put ur file there then click SHA1
        # and you will see ur hash at "File checksum:"
    gui:
        kick_when_close: true
        message: 'Choose a resource pack!'
gui:
    # The display gui's name
    gui_name: '§f§lMultiRP ( CHOOSE ONE )'
    # Paging Button Item Config
    next_page:
        id: 288
        data_value: 0
        name: '§f§lNext Page'
        lore:
        - '§fClick me to next page!'
    previous_page:
        id: 341
        data_value: 0
        name: '§f§lPrevious Page'
        lore:
        - '§fClick me to previous page!'
    paging:
        id: 54
        data_value: 0
        name: '§f§lPaging'
        lore:
        - '§aCurrent Page - §f%page'
```

# Sample ResourcePack

```
mrp:
    list_rp:
        default_pack: 
        # Put whatever name u want and not repeat.        
        # Its support multi page but the front will display first
        # so if any resourcepack want put at first page then put here.
        # and one page supported 45 resource_pack
        
            # Display Item Config
            id: 2
            data_value: 0
            name: '§fDefault Pack 1.11.2'
            lore:
            - '§7 Minecraft Default Pack'
            - '§a Version 1.11.2'
            # The resourcepack url
            url: https://www.dropbox.com/s/j0oltf2zqaz942i/1.11.2.zip?dl=1
            
            # The resourcepack hash
            hash: 391562CA4A83114577AECC4AC475EA6B2D16B6C9
            # You can get the hash via SHA1 ( URL = http://onlinemd5.com )
            # Open the link and put ur file there then click SHA1
            # and you will see ur hash at "File checksum:"
```
