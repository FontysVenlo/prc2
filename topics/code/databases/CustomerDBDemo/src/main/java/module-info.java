module customerdbdemo {
    
    requires java.logging;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires java.naming;  // Because setServerNames uses JNDI (Java Naming and Directory Interface)
    
}
