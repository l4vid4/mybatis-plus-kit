import io.github.l4vid4.generator.engine.CodeGenerator;
import io.github.l4vid4.generator.engine.ControllerGenerator;

public class Test {
    public static void main(String[] args) {
        String url = "jdbc:mysql://192.168.56.10:3306/mp?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";

        String username = "root";

        String password = "root";

        // 生成Entity，Service，Mapper
        new CodeGenerator()
                .dataSource(url, username, password)
                .packageName("io.github.l4vid4.example")
                .module("kit-example")
                .tables("user")
                .author("l4vid4")
                .build()
                .execute();

//        // 生成Controller
//        new ControllerGenerator()
//                .dataSource(url, username, password)
//                .packageName("io.github.l4vid4.example")
//                .module("kit-example")
//                .tables("user1")
//                .author("l4vid4")
//                .build()
//                .execute();
    }
}
