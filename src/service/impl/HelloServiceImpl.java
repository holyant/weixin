package service.impl;

import dao.HelloDao;
import service.HelloService;

public class HelloServiceImpl implements HelloService{
	private HelloDao helloDao;
	public void deleteEmp(int empno) throws Exception{
		this.helloDao.deleteEmp(empno);
	}
	public void setHelloDao(HelloDao helloDao) {
		this.helloDao = helloDao;
	}

}
