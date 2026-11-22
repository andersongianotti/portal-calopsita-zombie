# Calopsita — Addon para Minecraft Bedrock Edition

Addon que adiciona a **Calopsita**, um pet pássaro domesticável para ** consoles e mobile** (PS5, Xbox, Switch, Windows/Android/iOS).

## O que contém

```
bedrock-addon/
├── behavior_pack/          # lógica do jogo (entidade, spawn, domesticação)
│   ├── manifest.json
│   ├── entities/calopsita.entity.json
│   └── spawn_rules/calopsita.json
└── resource_pack/          # visual (modelo, textura, animações — reutiliza assets do periquito vanilla)
    ├── manifest.json
    ├── entity/calopsita.entity.client.json
    └── texts/              # nomes em en_US e pt_BR
```

## Funcionalidades

- Calopsita selvagem que **voa e pousa** como um periquito
- **Domesticável com sementes** (trigo, melancia, abóbora, beterraba) — 100% de chance
- Uma vez domesticada:
  - Segue o dono
  - Senta/levanta com clique
  - Anda no **ombro** do jogador
  - Aceita laço e Etiqueta (nomeável)
  - Cura com sementes
- Spawna naturalmente em **biomas de selva**
- Visual: modelo/animações do periquito (textura cinza) — personalizável depois

## Como instalar (PS5 / consoles)

1. Transfira as pastas `behavior_pack` e `resource_pack` para o console
   (por USB em `minecraftWorlds` não é o caminho — o jeito mais fácil é usar o app **MC Edit** ou enviar os arquivos compactados e ativar nos packs do mundo)
2. No Minecraft, crie um novo mundo (ou edite o existente)
   - **Behavior Packs** → Ative "Calopsita"
   - **Resource Packs** → Ative "Calopsita Resources"
3. No jogo:
   - Use `/summon calopsita:calopsita` para testar (precisa de cheats ativado)
   - Ou encontre uma Calopsita na selva e domestique com sementes

> **Importante:** para compactar em `.mcpack`, empacote cada pasta separadamente
> (zip de `behavior_pack` → `CalopsitaBP.mcpack`, zip de `resource_pack` → `CalopsitaRP.mcpack`).
> No PS5, arquivos `.mcpack` podem ser abertos com o Minecraft para importar.

## Jogando com amigos

- **Mundo single-player:** cada jogador aplica os dois packs no próprio mundo.
- **Realms:** ⚠️ o Realms **não aceita addons** — a Calopsita não funcionará lá.
- **Servidor de terceiros:** só funciona se o servidor aceitar behavior packs
  (a maioria dos servidores públicos não permite). Nesses casos, todos os
  jogadores precisam dos dois packs aplicados.

## Comandos úteis

```
/summon calopsita:calopsita ~ ~ ~        # spawnar uma Calopsita
/give @s spawn_egg 1                    # ovo gerador (se disponível)
```

## Personalização

Para textura/modelo próprios, edite `resource_pack/entity/calopsita.entity.client.json`
e adicione arquivos em `resource_pack/textures/entity/calopsita/` e
`resource_pack/models/entity/calopsita.geo.json` (criados com Blockbench).
