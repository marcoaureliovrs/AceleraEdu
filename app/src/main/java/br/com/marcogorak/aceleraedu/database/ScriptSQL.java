package br.com.marcogorak.aceleraedu.database;

/**
 * Created by MarcoGorak on 04/02/2016.
 */
public class ScriptSQL {

    public static String getCreateDataBase() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("");

        //Criando tabela usuario
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS tbl_usuario (" );
        sqlBuilder.append("cod_usuario INTEGER  NOT NULL ");
        sqlBuilder.append("PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("nome_usuario varchar(150) NOT NULL, ");
        sqlBuilder.append("email_usuario varchar(100) NOT NULL, ");
        sqlBuilder.append("senha_usuario varchar(15)  NOT NULL, ");
        //sqlBuilder.append("sexo_usuario varchar(30)  NOT NULL, ");
        //sqlBuilder.append("data_nascimento date NOT NULL, ");
        sqlBuilder.append("cod_nivel_usuario int(11) NOT NULL);");


/*
        //Criando a tabela aula
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS tbl_aula (");
        sqlBuilder.append("cod_aula int(11) NOT NULL AUTO_INCREMENT, ");
        sqlBuilder.append("cod_materia int(11) NOT NULL, ");
        sqlBuilder.append("nome_aula varchar(180) COLLATE latin1_general_ci NOT NULL, ");
        sqlBuilder.append("desc_aula varchar(300) COLLATE latin1_general_ci NOT NULL,");
        sqlBuilder.append("dependencia_aula int(11) NOT NULL, ");
        sqlBuilder.append("url_videoaula varchar(100) COLLATE latin1_general_ci NOT NULL, ");
        sqlBuilder.append("url_docaula varchar(100) COLLATE latin1_general_ci NOT NULL, ");
        sqlBuilder.append("url_pptaula varchar(100) COLLATE latin1_general_ci NOT NULL, ");
        sqlBuilder.append("PRIMARY KEY (cod_aula)");
        sqlBuilder.append(") ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=1;");


        //Criando a tabela curso
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS `tbl_curso` (");
        sqlBuilder.append("`cod_curso` int(11) NOT NULL AUTO_INCREMENT, ");
        sqlBuilder.append("`nome_curso` varchar(100) COLLATE latin1_general_ci NOT NULL, ");
        sqlBuilder.append("`descricao_curso` varchar(300) COLLATE latin1_general_ci NOT NULL, ");
        sqlBuilder.append("PRIMARY KEY (`cod_curso`)");
        sqlBuilder.append(") ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=1;");



        // Criando a tabela materia
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS `tbl_materia` (");
        sqlBuilder.append("`cod_materia` int(11) NOT NULL AUTO_INCREMENT, ");
        sqlBuilder.append("`nome_materia` varchar(100) COLLATE latin1_general_ci NOT NULL, ");
        sqlBuilder.append("`descricao_materia` varchar(300) COLLATE latin1_general_ci NOT NULL, ");
        sqlBuilder.append("`cod_curso_relacionado` int(11) NOT NULL, ");
        sqlBuilder.append("`dependencia_materia` int(11) NOT NULL, ");
        sqlBuilder.append("PRIMARY KEY (`cod_materia`)");
        sqlBuilder.append(") ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=1;");





*/

        return sqlBuilder.toString();
    }



}
