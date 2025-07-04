#!/bin/bash

# Absolute path to your Obsidian note
SOURCE="$HOME/Library/Mobile Documents/iCloud~md~obsidian/Documents/Obsidian/Scala Programming Language.md"
DEST="./README.md"

echo "[INFO] Watching: $SOURCE"
echo "[INFO] Target:   $DEST"

# Watch for changes and sync when modified
fswatch -o "$SOURCE" | while read -r _; do
  echo "[INFO] Change detected at $(date)"
  cp "$SOURCE" ./ && mv "./Scala Programming Language.md" "$DEST"
  echo "[INFO] README.md updated."
done
