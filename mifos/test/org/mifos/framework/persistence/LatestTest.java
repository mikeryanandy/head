package org.mifos.framework.persistence;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import junit.framework.TestCase;
import net.sourceforge.mayfly.Database;
import net.sourceforge.mayfly.dump.SqlDumper;

import org.mifos.framework.util.helpers.DatabaseSetup;

public class LatestTest extends TestCase {
	
	public void testSimple() throws Exception {
		Database database = new Database();
		loadLatest(database);
		String latestDump = new SqlDumper().dump(database.dataStore());

		database = new Database();
		applyUpgrades(database);
		String upgradeDump = new SqlDumper().dump(database.dataStore());
		
		assertEquals(latestDump, upgradeDump);
	}

	private void applyUpgrades(Database database) {
		database.execute("create table foo(x integer)");
		database.execute("insert into foo(x) values(5)");
		database.execute("alter table foo add column y integer default 7");
	}

	private void loadLatest(Database database) {
		database.execute("create table foo(x integer, y integer default 7)");
		database.execute("insert into foo(x, y) values(5,7)");
	}
	
	public void testRealSchema() throws Exception {
		Database database = new Database();
		loadRealLatest(database);
		String latestDump = new SqlDumper().dump(database.dataStore());

		database = new Database();
		applyRealUpgrades(database);
		String upgradeDump = new SqlDumper().dump(database.dataStore());
		
		assertEquals(latestDump, upgradeDump);
	}

	private void applyRealUpgrades(Database database) throws Exception {
	    DatabaseSetup.executeScript(database, "sql/mifosdbcreationscript.sql");
	    DatabaseSetup.executeScript(database, "sql/mifosmasterdata.sql");
	    DatabaseSetup.executeScript(database, "sql/rmpdbcreationscript.sql");
	    DatabaseSetup.executeScript(database, "sql/rmpmasterdata.sql");
	    DatabaseSetup.executeScript(database, "sql/Iteration13-DBScripts25092006.sql");
	    DatabaseSetup.executeScript(database, "sql/Iteration14-DDL-DBScripts10102006.sql");
	    DatabaseSetup.executeScript(database, "sql/Iteration14-DML-DBScripts10102006.sql");
	    DatabaseSetup.executeScript(database, "sql/Iteration15-DDL-DBScripts24102006.sql");
	    DatabaseSetup.executeScript(database, "sql/Iteration15-DBScripts20061012.sql");
	    DatabaseSetup.executeScript(database, "sql/add-version.sql");

	    runUpgradeScripts(database);
	}

	private void runUpgradeScripts(Database database) throws SQLException, IOException {
	    Connection conn = database.openConnection();
	    DatabaseVersionPersistence persistence = new DatabaseVersionPersistence() {
	    	@Override
	    	URL lookup(String name) {
	    		try {
					return new URL("file:sql/"+name);
				} catch (MalformedURLException e) {
					throw new RuntimeException(e);
				}
	    	}
	    };	    
	    int version  = persistence.read(conn);
	    assertEquals(100, version);
	    URL[] scripts = persistence.scripts(DatabaseVersionPersistence.APPLICATION_VERSION, version);
	    persistence.execute(scripts, conn);
	}

	private void loadRealLatest(Database database) {
	    DatabaseSetup.executeScript(database, "sql/latest-schema.sql");
	    DatabaseSetup.executeScript(database, "sql/latest-data.sql");
	}

	public void testDropTables() throws Exception {
		Database database = new Database();
		String blankDB = new SqlDumper().dump(database.dataStore());
		DatabaseSetup.executeScript(database, "sql/latest-schema.sql");
		DatabaseSetup.executeScript(database, "sql/mifosdroptables.sql");
		String cleanedDB = new SqlDumper().dump(database.dataStore());
		assertEquals(blankDB, cleanedDB);
	}

}
