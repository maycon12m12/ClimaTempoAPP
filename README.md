# Projeto de Previsão do Tempo

Este projeto foi desenvolvido como parte da Atividade Prática Orientada (APO) para a disciplina de Programação de Dispositivos Móveis. O objetivo é criar um aplicativo Android para previsão do tempo utilizando boas práticas de design e desenvolvimento em Java.

## Funcionalidades

- **Splash Screen**: Tela inicial de abertura exibida por 3 segundos.
- **TabBar**: Tela principal com duas abas:
  - **Aba de Previsão do Tempo**: Lista de previsão do tempo utilizando RecyclerView e CardView.
  - **Aba de Mapa**: Mapa com marcador indicando a cidade da previsão.
- **Menu na App Bar**: Inclui uma opção "Sobre" com informações pessoais.
- **Troca de Cidade**: Utiliza QR Code para alterar a cidade de consulta (implementado com a biblioteca ZXing).

## Tecnologias e Ferramentas

- **Linguagem**: Java
- **IDE**: Android Studio
- **UI Components**: RecyclerView, CardView, AppBar, Material Design
- **Bibliotecas**:
  - Retrofit para consumo da API de previsão do tempo
  - Google Maps API para exibir o mapa com marcador
  - ZXing para leitura de QR Code

## Estrutura de Telas

1. **Splash Screen**: Exibe o logo do aplicativo.
2. **Tela Principal com TabBar**:
   - **Aba 1**: Lista de previsão do tempo com visual em CardView.
   - **Aba 2**: Mapa mostrando a localização da cidade da previsão.
3. **Tela de Sobre**: Inclui informações pessoais (nome, RA, foto e curso).

## Requisitos

- **API de Previsão do Tempo**: [HG Brasil Weather API](https://console.hgbrasil.com/documentation/weather)
- **QR Code**: Biblioteca [ZXing](https://github.com/journeyapps/zxing-android-embedded)
- **Mapas**: [Google Maps API](https://developers.google.com/maps/documentation/android-sdk/start)

## Autor
- **Nome**: MAYCON JUAN CASSIANO DOS SANTOS.
-**RA**: 09044019.
-**Curso**: ANÁLISE E DESENVOLVIMENTO DE SISTEMAS.
-**Polo**: TOLEDO-PR.

