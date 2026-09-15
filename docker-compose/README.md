# Bash script to copy these folders to the docker stacks folder


Run these commands:

```bash
chmod +x copy-to-stacks-folder.sh

./copy-to-stacks-folder.sh
```

It will create:

```text
/opt/stacks/
├── dockage/
├── kafka/
└── postgres/
```

**Note:** This uses `cp -r`, so if those folders already exist in `/opt/stacks/`, files with the same names will be overwritten/merged.
