# 📱 Aplicação Android - Estrutura e Clean Architecture

Este projeto é uma aplicação Android desenvolvida com **Kotlin** e **Jetpack Compose**, com foco na **Clean Architecture** para uma organização clara e modular do código. A estrutura do projeto é organizada em módulos distintos para garantir que o código seja fácil de manter, testar e expandir.

## 📝 Visão Geral

Este projeto demonstra o uso da **Clean Architecture** aplicada a um aplicativo Android. Ele é estruturado para separar claramente as responsabilidades, com camadas distintas para **apresentação**, **domínio** e **dados**. A arquitetura limpa permite um desenvolvimento mais escalável e sustentável, com a possibilidade de trocar facilmente componentes sem afetar o restante do sistema.

## 📂 Estrutura de Pastas

A estrutura de pastas do projeto segue os princípios da **Clean Architecture** e é organizada da seguinte forma:
```bash
app/
└── src/
└── main/
└── java/com/example/noticiaAPI/
├── data/                        (Camada de Dados)
│   ├── remote/                  (Acesso a APIs e fontes externas)
│   ├── repository/              (Implementação de repositórios)
├── domain/                      (Camada de Domínio)
│   ├── model/                   (Modelos de dados de domínio)
│   ├── use_case/                (Casos de uso ou lógica de negócios)
├── presentation/                (Camada de Apresentação - UI)
│   ├── news_list/               (Tela de listagem de notícias)
│   ├── NewsViewModel.kt         (Gerenciamento de estado e lógica de UI)
└── theme/                       (Tema do aplicativo)
├── Color.kt                (Definição de cores)
├── Typography.kt           (Definição de tipografia)
├── Theme.kt                (Configuração geral de temas)
```

## 📂 Descrição das Pastas

- **data**: Contém todas as interações com fontes externas, como APIs e bancos de dados. A camada de dados é responsável pela recuperação e armazenamento de informações, implementando interfaces de repositório.
- **domain**: Esta camada contém a lógica de negócios do aplicativo. Ela define os modelos de dados e casos de uso, que são independentes de qualquer implementação específica de dados ou apresentação.
- **presentation**: A camada de apresentação lida com a interface do usuário e interações. Aqui você encontra o `ViewModel` que conecta a interface com a lógica de negócios e as telas específicas, como a tela de listagem de notícias.

## 🚀 Funcionalidades

- **Busca de notícias**: A aplicação permite buscar notícias com base em parâmetros como localidade e idioma.
- **Interface Moderna**: Utiliza **Jetpack Compose** para uma interface de usuário fluida e moderna.
- **Organização por Camadas**: O código está estruturado com base na **Clean Architecture**, separando claramente a lógica de negócios, dados e apresentação.

## 🛠️ Tecnologias Utilizadas

- **Kotlin**: Linguagem principal para desenvolvimento Android.
- **Jetpack Compose**: Framework moderno de UI para criar interfaces nativas.
- **Clean Architecture**: Arquitetura para organizar o código em camadas separadas de dados, domínio e apresentação.
- **Retrofit**: Para comunicação com APIs externas.
- **Material Design 3**: Para garantir que a interface seja moderna e consistente.
- **Android Studio**: IDE para desenvolvimento e teste.

## 🏃 Como Executar

Siga as etapas abaixo para configurar e executar o projeto:

1. Clone este repositório:
   ```bash
   git clone https://github.com/seu-usuario/projeto-clean-architecture.git
```
2. Abra o projeto no Android Studio.
3. Execute o aplicativo em um dispositivo ou emulador Android.
4. O aplicativo estará pronto para uso!

## 🔧 Melhorias Futuras

Algumas ideias para futuras melhorias:
- **Adicionar mais casos de uso:** Implementar mais funcionalidades de negócio.
- **Testes unitários:** Implementar testes para casos de uso e repositórios.
- **Suporte para múltiplas fontes de dados:** Tornar o repositório mais flexível, permitindo adicionar fontes de dados alternativas.
- **Interface mais interativa:** Melhorar a interação com o usuário, adicionando animações e transições.

## 📝 Contribuições

Contribuições são bem-vindas! Se você quiser melhorar este projeto, siga os passos abaixo:

1. Fork o projeto.
2. Crie uma branch para sua nova funcionalidade ou correção de bug:
   ```bash
   git checkout -b minha-nova-funcionalidade
```
3. Faça as modificações necessárias.
4. Faça um commit com suas mudanças:
   ```bash
   git commit -m 'Adiciona nova funcionalidade'
   ```
5. Envie para o repositório original:
   ```bash
   git push origin minha-nova-funcionalidade
   ```
6. Abra um Pull Request e descreva suas alterações.

## 📄 Licença

Este projeto é de código aberto e está licenciado sob a **MIT License**.

Desenvolvido com 💻 e ☕ por **Tomás Silva**.
