-- Empresa de exemplo
INSERT INTO TB_EMPRESA (NOME, CNPJ, SETOR, PAIS)
VALUES ('EcoTech Brasil', '12.345.678/0001-99', 'Tecnologia', 'Brasil');

-- Usuário admin inicial (senha: admin123 em BCrypt)
INSERT INTO TB_USUARIO (NOME, EMAIL, SENHA, ROLE)
VALUES (
           'Administrador',
           'admin@esg.com',
           '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.',
           'ADMIN'
       );

COMMIT;