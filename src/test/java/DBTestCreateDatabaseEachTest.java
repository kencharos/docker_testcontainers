import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.junit.*;
import org.testcontainers.containers.GenericContainer;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 * テストごとに、DBコンテナを毎回生成します。
 * コンテナはテストメソッドごとに生成・破棄されますので、トランザクション制御していなくても、
 * データは元に戻ります。
 *
 * Docker machine を使用している場合は、環境変数の設定が必要
Docker Toolbox を使用している場合、Docker quickstart を実行して、`env | grep DOCKER ` を実行すると何が設定されているかわかります。
 サンプル)
 DOCKER_HOST=tcp://192.168.99.100:2376
 DOCKER_MACHINE_NAME=default
 DOCKER_TLS_VERIFY=1
 DOCKER_CERT_PATH=<your home>\.docker\machine\machines\default
 */
public class DBTestCreateDatabaseEachTest {

    @Rule
    public GenericContainer testdb = new GenericContainer("testdb:1.0.0")
            .withExposedPorts(5432);

    @Before
    public void initConnection() throws Exception{

        String host = testdb.getContainerIpAddress();
        int port = testdb.getMappedPort(5432);
        String db = "postgres";
        String user = db;
        String pass = "";
        String url = "jdbc:postgresql://" + host + ":" + port + "/" + db;

        Connection c = DriverManager.getConnection(url, user, pass);
        c.setAutoCommit(true);
        DatabaseConnection con = new DatabaseConnection(c);
        con.getConfig().setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
        con.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY , new PostgresqlDataTypeFactory());

        this.con =con;
    }

    @After
    public void rollback() throws Exception{
        this.con.getConnection().close();
    }

    private IDatabaseConnection con;

    @Test
    public void testSelect() throws Exception{

        IDataSet res =  con.createDataSet();

        ITable product = res.getTable("PRODUCT");
        assertThat(product.getRowCount(), is(1));

        ITable div = res.getTable("DIV");

        assertThat(div.getRowCount(), is(4));
    }


    @Test
    public void testInsert() throws Exception{

        con.getConnection().prepareStatement("INSERT INTO PRODUCT VALUES(8, 'まな板', 3, 1)").executeUpdate();

        IDataSet res =  con.createDataSet();
        ITable product = res.getTable("PRODUCT");
        assertThat(product.getRowCount(), is(2));
        assertThat(product.getValue(1, "name"), is("まな板"));
    }


}
