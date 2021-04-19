package br.com.laboratory.service;

import br.com.laboratory.entity.Exam;
import br.com.laboratory.repository.ExamRepository;
import br.com.laboratory.service.ExamService;
import br.com.laboratory.util.ExamType;
import br.com.laboratory.util.Status;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class ExamServiceTest {

    @InjectMocks
    private ExamService examService;

    @Mock
    private ExamRepository examRepository;


    @Before
    public void setup(){
        org.springframework.test.util.ReflectionTestUtils.setField(examService, "maxItemsPerPage", 50);
    }


    @Test
    public void updateSuccess(){

        Exam examToUpdate = Exam.builder().id(1).name("Update Name").type(ExamType.CLINIC).status(Status.ACTIVE).build();

        Exam examDB = Exam.builder().id(1).name("Teste name").type(ExamType.IMAGE).status(Status.ACTIVE).build();

        Mockito.when(examRepository.findById(any())).thenReturn(Optional.of(examDB));
        Mockito.when(examRepository.save(any())).thenReturn(examToUpdate);

        Exam exam = examService.update(examToUpdate,0);

        Assert.assertEquals(exam.getName(),examToUpdate.getName());
        Assert.assertEquals(exam.getType(),examToUpdate.getType());
        Assert.assertEquals(exam.getStatus(),Status.ACTIVE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateSetInativeStatus(){


        Exam examToUpdate = Exam.builder().id(1).name("Update Name").type(ExamType.CLINIC).status(Status.INATIVE).build();

        Exam examDB = Exam.builder().id(1).name("Teste name").type(ExamType.IMAGE).status(Status.ACTIVE).build();

        Mockito.when(examRepository.findById(any())).thenReturn(Optional.of(examDB));


        examService.update(examToUpdate,0);

        Mockito.verify(examRepository,Mockito.times(0)).save(any());

    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void inactiveNotFound(){


        Mockito.when(examRepository.findById(any())).thenThrow( new EmptyResultDataAccessException("Não existe o campo",1));

        examService.inactivate(1);

        Mockito.verify(examRepository,Mockito.times(0)).save(any());

    }

    @Test
    public void inactiveSuccess(){

        Exam examDB = Exam.builder().id(1).name("Teste name").type(ExamType.IMAGE).status(Status.ACTIVE).build();

        Mockito.when(examRepository.findById(any())).thenReturn(Optional.of(examDB));

        examService.inactivate(1);

        Mockito.verify(examRepository,Mockito.times(1)).save(any());

    }

    @Test
    public void findByOnePage(){

        Exam examDB = Exam.builder().id(1).name("Teste name").type(ExamType.IMAGE).status(Status.ACTIVE).build();

        Mockito.when(examRepository.findByStatus(any(),any())).thenReturn( new PageImpl(List.of(examDB)));

        Page<Exam> pagedResponse =  examService.findByStatusPageable(1);

        Assert.assertTrue(pagedResponse.hasContent());

    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void findNotFound(){

        Mockito.when(examRepository.findByStatus(any(),any())).thenReturn( new PageImpl(List.of()));

        examService.findByStatusPageable(1);

    }






}
