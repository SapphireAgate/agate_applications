home_dir=/scratch/aaasz/agate/agate_code/agate_applications/Calendar/cosmo_bin
home_dir_libs=${home_dir}/libs/jetty_libs/dex_jars

./dalvik_android -Xmx512m -Xms256m \
	-classpath ${home_dir_libs}/startup.jar:${home_dir_libs}/i-jetty-server-3.2-SNAPSHOT.jar:${home_dir_libs}/jetty-client-7.6.0.RC4.jar:${home_dir_libs}/jetty-continuation-7.6.0.RC4.jar:${home_dir_libs}/jetty-deploy-7.6.0.RC4.jar:${home_dir_libs}/jetty-http-7.6.0.RC4.jar:${home_dir_libs}/jetty-io-7.6.0.RC4.jar:${home_dir_libs}/jetty-security-7.6.0.RC4.jar:${home_dir_libs}/jetty-server-7.6.0.RC4.jar:${home_dir_libs}/jetty-servlet-7.6.0.RC4.jar:${home_dir_libs}/jetty-util-7.6.0.RC4.jar:${home_dir_libs}/jetty-webapp-7.6.0.RC4.jar:${home_dir_libs}/jetty-xml-7.6.0.RC4.jar:${home_dir_libs}/servlet-api-2.5.jar \
	com.jetty.StartJetty
