home_dir=/scratch/aaasz/agate/agate_code/agate_applications/Chat/openfire_bin/target/openfire
home_dir_libs=${home_dir}/lib/dex_jars

./dalvik_android -Xmx512m -Xms256m \
	-DopenfireHome=${home_dir} \
	-Dlog4j.configuration=file:///scratch/aaasz/agate/agate_code/agate-4.3_r1/example_apps/Chat/openfire_bin/target/openfire/lib/log4j.properties \
	-Dopenfire.lib.dir=${home_dir_libs} \
	-classpath ${home_dir_libs}/startup.jar:${home_dir_libs}/openfire.jar:${home_dir_libs}/slf4j-log4j12.jar:${home_dir_libs}/activation.jars:${home_dir_libs}/hsqldb.jar:${home_dir_libs}/mail.jar:${home_dir_libs}/servlet.jar:${home_dir_libs}/commons-el.jar:${home_dir_libs}/jasper-runtime.jar:${home_dir_libs}/jasper-compiler.jar:${home_dir_libs}/jdic.jar:${home_dir_libs}/jtds.jar:${home_dir_libs}/jndi.jar:${home_dir_libs}/beans.jar:${home_dir_libs}/harmony_rmi.jar:${home_dir_libs}/bouncycastle2.jar:${home_dir_libs}/mysql.jar \
	org.jivesoftware.openfire.starter.ServerStarter
