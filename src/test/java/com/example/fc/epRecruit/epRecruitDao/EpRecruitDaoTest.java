package com.example.fc.epRecruit.epRecruitDao;

import com.example.fc.epRecruit.epRecruitVo.EpRecruitLeftJoinMainThumbnailVO;
import com.example.fc.epRecruit.epRecruitVo.EpRecruitVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
@Slf4j
class EpRecruitDaoTest {

    @Autowired
    private EpRecruitDao epRecruitDao;

    @Test
    public void LastIdTest() {
        Long res = epRecruitDao.epRecruitLastId();
        System.out.println(res);
    }

    @Test
    void epListTest() {
        List<EpRecruitLeftJoinMainThumbnailVO> lst = epRecruitDao.epRecruitMainList();
        for ( EpRecruitLeftJoinMainThumbnailVO ep : lst.subList(1, 20)) {
            System.out.println(ep);
        }
    }

    @Test
    void epFindByStackListTest() {
//        List<EpRecruitVO> lst = epRecruitDao.epFindByStackList("java");

//        log.info(lst.toString());
    }

}