package com.example.exambuilder.service;

import com.example.exambuilder.entity.Enum.QuestionType;
import com.example.exambuilder.entity.Evaluation;
import com.example.exambuilder.entity.Question;
import com.example.exambuilder.entity.QuestionAlternative;
import com.example.exambuilder.entity.Teacher;
import com.example.exambuilder.exceptions.EvaluationNotFoundException;
import com.example.exambuilder.exceptions.TeacherNotFoundException;
import com.example.exambuilder.repository.EvaluationRepository;
import com.example.exambuilder.repository.QuestionRepository;
import com.example.exambuilder.repository.TeacherRepository;
import com.example.exambuilder.web.dto.EvaluationCreateDto;
import com.example.exambuilder.web.dto.EvaluationResponseDto;
import com.example.exambuilder.web.dto.mapper.EvaluationMapper;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final TeacherRepository teacherRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public EvaluationResponseDto create(EvaluationCreateDto createDto) {
        Teacher teacher = teacherRepository.findById(createDto.getTeacherId()).orElseThrow(
                () -> new TeacherNotFoundException("Teacher not found!")
        );

        List<Question> questions = questionRepository.findAllById(createDto.getQuestionIds());

        Evaluation evaluation = new Evaluation();

        evaluation.setDiscipline(createDto.getDiscipline());
        evaluation.setCreatedAt(LocalDateTime.now());
        evaluation.setGradeLevel(createDto.getGradeLevel());
        evaluation.setInstitution(createDto.getInstitution());
        evaluation.setTeacher(teacher);
        evaluation.setQuestions(questions);

        evaluationRepository.save(evaluation);

        return EvaluationMapper.toDto(evaluation);
    }

    @Transactional
    public EvaluationResponseDto update(Long id, EvaluationCreateDto createDto) {
        Evaluation evaluation = evaluationRepository.findById(id).orElseThrow(
                () -> new EvaluationNotFoundException("Evaluation not found!")
        );
        Teacher teacher = teacherRepository.findById(createDto.getTeacherId()).orElseThrow(
                () -> new TeacherNotFoundException("Teacher not found!")
        );

        List<Question> questions = questionRepository.findAllById(createDto.getQuestionIds());

        evaluation.setDiscipline(createDto.getDiscipline());
        evaluation.setGradeLevel(createDto.getGradeLevel());
        evaluation.setInstitution(createDto.getInstitution());
        evaluation.setTeacher(teacher);
        evaluation.setQuestions(questions);

        evaluationRepository.save(evaluation);

        return EvaluationMapper.toDto(evaluation);
    }

    @Transactional
    public List<EvaluationResponseDto> findAll() {
        return EvaluationMapper.toListDto(evaluationRepository.findAll());
    }

    @Transactional
    public EvaluationResponseDto findById(Long id) {
        Evaluation evaluation = evaluationRepository.findById(id).orElseThrow(
                () -> new EvaluationNotFoundException("Evaluation not found!")
        );

        return EvaluationMapper.toDto(evaluation);
    }

    @Transactional
    public void delete(Long id) {
        Evaluation evaluation = evaluationRepository.findById(id).orElseThrow(
                () -> new EvaluationNotFoundException("Evaluation not found!")
        );
        evaluationRepository.delete(evaluation);
    }

    @Transactional(readOnly = true)
    public byte[] generatePdf(Long id) {

        Evaluation evaluation = evaluationRepository
                .findByIdWithQuestionsAndAlternatives(id)
                .orElseThrow(() ->
                        new EvaluationNotFoundException("Evaluation not found!"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {

            Document document = new Document();

            PdfWriter.getInstance(document, outputStream);

            document.open();

            document.add(new Paragraph("PROVA"));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Instituição: " + evaluation.getInstitution()));
            document.add(new Paragraph("Disciplina: " + evaluation.getDiscipline()));
            document.add(new Paragraph("Série/Turma: " + evaluation.getGradeLevel()));

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Professor: " + evaluation.getTeacher().getName()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Nome do Aluno: _______________________________________"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Data: ____/____/________"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("_______________________________________________________"));
            document.add(new Paragraph(" "));

            int numeroQuestao = 1;

            for (Question question : evaluation.getQuestions()) {

                System.out.println("--------------------------------");
                System.out.println("Questão ID: " + question.getId());
                System.out.println("Questão: " + question.getStatement());
                System.out.println("Tipo: " + question.getType());

                if (question.getAlternatives() != null) {
                    System.out.println("Qtd alternativas: "
                            + question.getAlternatives().size());
                }

                document.add(
                        new Paragraph(
                                numeroQuestao + ") " + question.getStatement()
                        )
                );

                document.add(new Paragraph(" "));

                if (question.getType() == QuestionType.FECHADA) {

                    if (question.getAlternatives() != null
                            && !question.getAlternatives().isEmpty()) {

                        char letra = 'A';

                        for (QuestionAlternative alternative : question.getAlternatives()) {

                            document.add(
                                    new Paragraph(
                                            letra + ") " + alternative.getText()
                                    )
                            );

                            letra++;
                        }
                    } else {

                        document.add(
                                new Paragraph(
                                        "[Nenhuma alternativa encontrada]"
                                )
                        );
                    }

                    document.add(new Paragraph(" "));
                } else {

                    document.add(
                            new Paragraph(
                                    "____________________________________________________"
                            )
                    );

                    document.add(
                            new Paragraph(
                                    "____________________________________________________"
                            )
                    );

                    document.add(
                            new Paragraph(
                                    "____________________________________________________"
                            )
                    );

                    document.add(new Paragraph(" "));
                }

                numeroQuestao++;
            }

            document.close();

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF da avaliação.", e);
        }
    }
}