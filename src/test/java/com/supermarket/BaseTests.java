package com.supermarket;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional  // 确保测试方法在一个事务的上下文中执行
@Rollback   // 用于控制事务的提交或回滚，value属性设置为false，使得事务在测试完成后提交而不是回滚
public class BaseTests {
}
