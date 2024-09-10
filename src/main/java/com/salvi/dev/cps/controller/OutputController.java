package com.salvi.dev.cps.controller;

import com.salvi.dev.cps.application.MBAMGApplication;
import com.salvi.dev.cps.fileupload.StorageFileNotFoundException;
import com.salvi.dev.cps.fileupload.StorageService;
import com.salvi.dev.cps.service.Metric;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.stereotype.Controller;

@Controller
public class OutputController {
    private final StorageService storageService;

    @Autowired
    public OutputController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(OutputController.class,
                        "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));
        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);

        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
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
            // return ResponseEntity.ok("Files uploaded and processed successfully.");
        } catch (IOException e) {
            // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed
            // to process files.");
        }
        // storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "Results for metric b: " + outpuMetric.getB());
        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
    // @GetMapping("/")
    // public String listUploadedFiles() throws IOException {
    // return "fileuploader";
    // }

    // @GetMapping("/output")
    // public com.salvi.dev.cps.service.Metric getOutput() {
    // System.out.println("OutputController.getOutput");
    // MBAMGApplication mbamgApplication = new MBAMGApplication();
    // com.salvi.dev.cps.service.Metric outputMetric = mbamgApplication.getMetric();
    // return outputMetric;
    // }

    // @PostMapping("/upload")
    // @ResponseBody
    // public com.salvi.dev.cps.service.Metric uploadFiles(@RequestParam("channels")
    // MultipartFile channelsFile,
    // @RequestParam("parameters") MultipartFile parametersFile) {
    // try {
    // // Process channels.txt
    // List<String> channelLines = new BufferedReader(new
    // InputStreamReader(channelsFile.getInputStream()))
    // .lines().collect(Collectors.toList());

    // // Process parameters.txt
    // List<String> paramLines = new BufferedReader(new
    // InputStreamReader(parametersFile.getInputStream()))
    // .lines().collect(Collectors.toList());

    // // Add your processing logic here
    // MBAMGApplication mbamgApplication = new MBAMGApplication();
    // return mbamgApplication.getMetric(channelLines, paramLines);
    // // return ResponseEntity.ok("Files uploaded and processed successfully.");
    // } catch (IOException e) {
    // // return
    // ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed
    // // to process files.");
    // }
    // return null;
    // }

    // // New code block
    // private static class Channel {
    //     // Channel implementation
    // }

    // private static class Metric {
    //     // Metric implementation
    // }
}