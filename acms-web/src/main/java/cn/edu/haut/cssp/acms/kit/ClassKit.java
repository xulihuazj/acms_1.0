package cn.edu.haut.cssp.acms.kit;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassKit {
	private static Logger logger = LoggerFactory.getLogger(ClassKit.class);

	public static List<Class<?>> getAllInterfaces(Class<?> cls) {
		if (cls == null) {
			return null;
		}

		LinkedHashSet interfacesFound = new LinkedHashSet();
		getAllInterfaces(cls, interfacesFound);

		return new ArrayList(interfacesFound);
	}

	private static void getAllInterfaces(Class<?> cls, HashSet<Class<?>> interfacesFound) {
		while (cls != null) {
			Class[] interfaces = cls.getInterfaces();

			for (Class i : interfaces) {
				if (interfacesFound.add(i)) {
					getAllInterfaces(i, interfacesFound);
				}
			}

			cls = cls.getSuperclass();
		}
	}

	public static Set<Class<?>> getClasses(String packageName) {
		Set classes = new LinkedHashSet();

		boolean recursive = true;

		String packageDirName = packageName.replace('.', '/');
		try {
			Enumeration dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);

			while (dirs.hasMoreElements()) {
				URL url = (URL) dirs.nextElement();

				String protocol = url.getProtocol();

				if ("file".equals(protocol)) {
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");

					findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
				} else if ("jar".equals(protocol)) {
					try {
						JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();

						Enumeration entries = jar.entries();

						while (entries.hasMoreElements()) {
							JarEntry entry = (JarEntry) entries.nextElement();
							String name = entry.getName();

							if (name.charAt(0) == '/') {
								name = name.substring(1);
							}

							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf(47);

								if (idx != -1) {
									packageName = name.substring(0, idx).replace('/', '.');
								}

								if ((((idx != -1) || (recursive))) && (name.endsWith(".class"))
										&& (!(entry.isDirectory()))) {
									String className = name.substring(packageName.length() + 1, name.length() - 6);
									try {
										classes.add(Class.forName(packageName + '.' + className));
									} catch (ClassNotFoundException e) {
										logger.error("添加用户自定义视图类错误 找不到此类的.class文件");
									}
								}
							}
						}
					} catch (IOException e) {
						logger.error("在扫描用户定义视图时从jar包获取文件出错");
					}
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		return classes;
	}

	private static void findAndAddClassesInPackageByFile(String packageName, String packagePath, boolean recursive,
			Set<Class<?>> classes) {
		File dir = new File(packagePath);

		if ((!(dir.exists())) || (!(dir.isDirectory()))) {
			logger.warn("用户定义包名 " + packageName + " 下没有任何文件");
			return;
		}

		File[] dirfiles = dir.listFiles(new FileFilter(recursive) {
			public boolean accept(File file) {
				return (((this.val$recursive) && (file.isDirectory())) || (file.getName().endsWith(".class")));
			}
		});
		for (File file : dirfiles) {
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive,
						classes);
			} else {
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					classes.add(
							Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
				} catch (ClassNotFoundException e) {
					logger.error("添加用户自定义视图类错误 找不到此类的.class文件");
				}
			}
		}
	}
}