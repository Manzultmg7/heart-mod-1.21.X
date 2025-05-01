# 💖 Heart Mod (Minecraft Fabric Mod)

A custom Fabric mod that introduces new gameplay mechanics where players can **unlock and manage extra hearts** using special keys. This mod is designed for **Minecraft 1.21** and built using **Java 21**.

---

## ✨ Features

- **Start with 3 hearts** by default (hardcore style)
- Use custom items to **unlock or remove hearts**
- Maximum of **10 hearts (20 HP)** per player
- Hearts persist across **death** and are saved between worlds
- Supports **multiplayer** with in-game broadcasts
- Beautiful **custom particles and sounds** for immersive feedback
- **Custom GUI overlay** when using the Super Key

---

## 🔑 Custom Items

### ❤️ **Heart Key**
- Unlocks **+1 heart (2 HP)** per use
- Plays a **special End Portal activation sound**
- Shows heart particles
- Broadcasts a message:  
  *“Player X unlocked a new heart!”*

### 💀 **Cursed Key**
- **Removes 1 heart (2 HP)** per use
- Minimum health is **1 heart (2 HP)**
- Plays wither hurt sound + smoke particles
- Broadcasts a message:  
  *“Player X lost a heart...”*

### 🌟 **Super Key**
- Unlocks **+3 hearts (6 HP)** at once
- Totem-like animation (custom overlay + particles)
- Broadcasts:  
  *“Player X unlocked 3 hearts using a Super Key!”*

---

## 🛠️ Technical Info

| Field               | Value                    |
|---------------------|--------------------------|
| **Minecraft Version** | 1.21                      |
| **Java Version**      | 21                       |
| **Mod Loader**        | Fabric                   |
| **Fabric API**        | Latest compatible        |

---

## 📂 Mod Structure

- `HeartKeyItem.java` – logic for adding hearts
- `CursedKeyItem.java` – logic for removing hearts
- `SuperKeyItem.java` – logic for unlocking 3 hearts + animation
- `PlayerEntityMixin.java` – manages heart bonuses and persistence
- `SuperKeyOverlay.java` – displays the custom overlay animation

---

## ✅ How to Use

1. Install **Fabric Loader** & **Fabric API** for Minecraft 1.21
2. Drop the built `.jar` into your `mods` folder
3. Launch the game
4. Access the keys via **creative mode** or add recipes/drops as needed!

---

## 🚀 Credits

- **Developed by:** Manjul Tamang
- **Source Code:** https://github.com/Manzultmg7/heart-mod-1.21.X.git

---

Enjoy unlocking your full heart potential! 💖
