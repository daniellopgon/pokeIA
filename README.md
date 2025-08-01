# PokéIA – Competitive Engine README

Light-weight Java 17 toolkit for **Pokémon Showdown** that reads your team + opponent’s team (JSON) and tells you the best move, lead, switch, or build.

## ✨ What it does
- **Damage calculator** – physical, special, STAB, type effectiveness  
- **Status engine** – sleeps, boosts, hazards, weather  
- **Meta stats** – count items / moves / abilities from top-tier sets  
- **Team builder** – auto-select 4-member squads for 4 archetypes  
- **Live advice** – “Use Heatran Lava Plume vs Ferrothorn for 84 % OHKO”

## 🧩 Core classes
| Class | Job |
|-------|-----|
| `Pokemon` | Holds name, types, stats, 4 moves, item, ability, strategy tag |
| `Efecto` | Wrapper for a move / item / ability + its side effects |
| `AccionEfecto` | Single stat change (e.g. +1 Atk to user, -50 % HP to foe) |
| `Estadistica` | HP / Atk / Def / SpA / SpD / Spe container |
| `Calculo` | Pure functions: damage, effect score, type chart |
| `Combate` | State machine: apply effects, resolve turns, choose best 4 |
| `DatosRival` | I/O: load JSON, prompt user, merge full move data |
| `EstadisticaCombate` | Pretty print usage tables |


## 📁 JSON contract (minimal)
```json
{
  "nombre": "Rotom-Wash",
  "tipos": ["Electric","Water"],
  "estadistica": { "vida": 50, "ataque": 65, … },
  "estrategia": "soporte y control de campo",
  "efectos": {
    "movimiento": [
      { "nombre":"Volt Switch", "tipoMovimiento":"especial",
        "efectos":[{"estadistica":"vida","modificador":"reducir","valor":65,"objetivo":"enemigo"}]
      }
    ]
  }
}
