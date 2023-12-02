# Login_Google
Este projeto se trata de um aplicativo simples para demonstração de um login feito utilizando a API do Google e o Firebase Authenticator.

**Passo a passo para a configuração do projeto:**

- No Android Studio, crie um novo projeto com um modelo de Empty Activity.

Configuração da Tela de Login:
- No build.grade (app) → coloca essa dependencia: implementation("com.google.android.gms:play-services-auth:20.7.0")
- activity_main.xml → Desenvolva a Tela de Login
​
Configuração do Firebase Authentication:
- Vá para Tools > Firebase > Autenticação > Autenticação com o Google no Android Studio.
- Siga as etapas para adicionar o SDK de Autenticação Firebase ao seu aplicativo.
- Add the Firebase Authenticatuon SDK to your app
- Connect your app to Firebase

Criando um projeto e conectando-o ao Android Studio:
- Acesse o site do Firebase e crie um novo projeto.
- Vá para Build > Authentication > Get Started.
- Selecione e ative a opção de login com o Google.
- Adicione seu e-mail ao campo correspondente e salve as alterações.
  
Configuração do SHA1:
- No Android Studio, acesse a aba do Gandle no canto esquerdo.
- Clique no gandle e irá aparece um campo para busca → digite ‘signingReport’.
- Copie o SHA1 que aparece no terminal.
- No Console do Firebase, vá para as configurações do projeto e adicione o SHA1 na seção "SHA certificate fingerprints", depois salve as alterações.
- Configuração no Android Studio:
- Selecione novamente o app no Android Studio (ao lado do botão de execução).
- Vá para Build e selecione Clean Project.

Adição de Dependências:
- No Console do Firebase, vá para a documentação (Acesse através de um ícone ao lado do sininho no canto superior direito).
- Vá para Build > Authentication.
- No menu lateral esquerdo selecione a seção do Android, clique em Google e copie as três dependências listadas.
- Adicione as dependências no arquivo build.gradle (app).

Veja a seguir os metodos que foram implementados para o funcionamento da autenticação nos arquivos do projeto... 
