package com.salvi.dev.cps.controller;

import com.salvi.dev.cps.application.MBAMGApplication;
import com.salvi.dev.cps.service.Metric;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.stereotype.Controller;

@Controller
public class OutputController {

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {
        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("channels") MultipartFile channelsFile,
            @RequestParam("parameters") MultipartFile parametersFile,
            RedirectAttributes redirectAttributes) {
        Metric outpuMetric = new Metric();

        try {
            // Process channels.txt
            List<String> channelLines = new BufferedReader(new InputStreamReader(channelsFile.getInputStream()))
                    .lines().collect(Collectors.toList());

            // Process parameters.txt
            List<String> paramLines = new BufferedReader(new InputStreamReader(parametersFile.getInputStream()))
                    .lines().collect(Collectors.toList());

            // Add your processing logic here
            MBAMGApplication mbamgApplication = new MBAMGApplication();
            outpuMetric = mbamgApplication.getMetric(channelLines, paramLines);

        } catch (IOException | MaxUploadSizeExceededException e) {
            e.printStackTrace();
        }
        redirectAttributes.addFlashAttribute("message",
                "Results for metric b: " + outpuMetric.getB());
        return "redirect:/";
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity maxUploadSizeExceeded(MaxUploadSizeExceededException e) {
        return ResponseEntity.badRequest().body("File size limit exceeded");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity illegalFileCompositions(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity errorProcessingfiles(IndexOutOfBoundsException e) {
        return ResponseEntity.badRequest().body("Error processing files");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity runtimeErrorProcessingfiles(RuntimeException e) {
        return ResponseEntity.badRequest().body("Runtime Error processing files");
    }
}