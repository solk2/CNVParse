# CNVParse
Very simple app to parse Sony modem config nvpreload files

This tool can be used to parse nvpreload files located in Sony modem configurations.

Step by step instructions:
1. First get needed config from Sony system partition. They are located at /system/etc/customization/modem.
2. Then you need t strip Sony specific header from it.
   To do that remve first 552 bytes from this file. Or simply run tail -c +553 $file_name > $dest_file.
3. After this you will have pretty standard tar archive. So just unpack it.
4. There you will have nvpreload folder usually with 3 tar files.
   Take the base one with shortest name and usually the biggest.
   Unpack it.
5. Build this project with mvn clean package.
6. This app requires nvitems_list.csv in the working dir and it will unpack everything to working dir.
7. Run it like this java -jar <path to unpacked nvpreload file>
8. That's all. You will have out.xml with NV Items with some garbage and nv folder with NV Files.
