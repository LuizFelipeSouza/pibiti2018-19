# Descrição do aplicativo
O propósito é demonstrar alguns serviços de Inteligência Artificial do Google Cloud através de um aplicativo que armazena fotos e textos. Estou utilizando a linguagem Java para o desenvolvimento, o Storage Access Framework, para acesso e armazenamento de imagens e o Navigation Component para a transição entre telas.

A primeira tela do aplicativo é composta por uma blank activity (figura 1). A partir dela, o usuário pode tocar no floating button e então ser encaminhado para a galeria de fotos do seu dispositivo, na qual poderá selecionar uma imagem. A imagem selecionado é então exibida na próxima tela (figura 2). Essa tela também possui um text input para que o usuário adcione uma legenda à imagem. Ao tocar no botão de enviar, a postagem é persistida e exibida em uma recyclerview (figura 3). Cada item dessa RecyclerView é um item clicável que direciona o usuário a uma tela com o texto e a imagem que foi adicionado anteriormente (figura 4). 

A tela de exibição das postagens conta também com uma busca, na qual o usuário pode digitar “dias em que estava alegre", "fotos na praia", "fotos em que eu estava sorrindo", etc. Essa busca será processada pelas ferramentas de IA do Google Cloud e as postagens correspondentes serão exibidas.

![Figura 1](https://github.com/luizsci42/Diario-de-Fotos-PIBITI-2018/assets/6129339/e019a2cd-0cbb-413e-85f2-4a43b342e28d)

![Figura 2](https://github.com/luizsci42/Diario-de-Fotos-PIBITI-2018/assets/6129339/ff7d399f-3adf-4758-8a63-9057b9326faa)

![Figura 3](https://github.com/luizsci42/Diario-de-Fotos-PIBITI-2018/assets/6129339/0f1f7940-8dee-409a-86e7-c45ac9756101)

![Figura 4](https://github.com/luizsci42/Diario-de-Fotos-PIBITI-2018/assets/6129339/d53457bf-20e7-4c21-8f10-c3640755af79)
