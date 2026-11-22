# Calopsita Portal

Mod Fabric para Minecraft 26.3 que adiciona um **Portal Sombrio**: ao atravessá-lo,
você é teleportado para uma dimensão escura infestada de **Calopsitas Zumbis**
hostis que atacam jogadores.

## Conteúdo

- **Portal Sombrio** (`calopsita_portal:dark_portal`): bloco sem colisão; ao entrar
  nele você viaja para a dimensão `calopsita_portal:dark`. Um portal de retorno
  é colocado automaticamente ao lado do ponto de chegada.
- **Dimensão Sombria**: mundo próprio gerado com terreno normal, mas com céu
  preto, sem luz do sol e clima sombrio — monstros surgem o tempo todo.
- **Calopsita Zumbi** (`calopsita_portal:zombie_calopsita`): ave hostil
  (variante do periquito) que persegue e ataca jogadores com ataque corpo a corpo.
  Spawn natural na dimensão sombria + ovo gerador na aba Spawn Eggs.

## Instalação

1. Minecraft Java Edition **26.3** + Fabric Loader + Fabric API
2. Copie `calopsita-portal-1.0.0.jar` para `.minecraft/mods`
3. No criativo: aba **Functional Blocks** → Portal Sombrio; aba **Spawn Eggs** →
   Ovo Gerador de Calopsita Zumbi

## Build

```bash
JAVA_HOME=<jdk25> ./gradlew build
```
