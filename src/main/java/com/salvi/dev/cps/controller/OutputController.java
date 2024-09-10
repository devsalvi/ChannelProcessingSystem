package com.salvi.dev.cps.controller;

import com.salvi.dev.cps.application.MBAMGApplication;
import com.salvi.dev.cps.service.ChannelProcessorUtils;
import com.salvi.dev.cps.service.Metric;
import com.salvi.dev.cps.service.Output;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.Channel;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
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
    MultipartFile channelsFile;
    MultipartFile parametersFile;

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
            @RequestParam("parameters") MultipartFile parametersFile, @RequestParam("operation") int operation,
            RedirectAttributes redirectAttributes) throws Exception {
        Metric outpuMetric = new Metric();
        Output output = new Output();
        ChannelProcessorUtils channelProcessorUtils = new ChannelProcessorUtils();  
        this.channelsFile = channelsFile;
        this.parametersFile = parametersFile;
        try {
            System.out.println("Operation: " + operation);
            // Process channels.txt
            List<String> channelLines = new BufferedReader(new InputStreamReader(channelsFile.getInputStream()))
                    .lines().collect(Collectors.toList());

            // Process parameters.txt
            List<String> paramLines = new BufferedReader(new InputStreamReader(parametersFile.getInputStream()))
                    .lines().collect(Collectors.toList());
System.out.println("Switch Operation");
            // Handle the selected operation
            switch (operation) {
                case 1:
                    // Execute function 1
                    output = channelProcessorUtils.executeFunction1(channelLines, paramLines);
                    redirectAttributes.addFlashAttribute("message",
                    "Output: " + output);
                    break;
                case 2:
                    // Execute function 2
                    output = channelProcessorUtils.executeFunction2(channelLines, paramLines);
                    redirectAttributes.addFlashAttribute("message",
                    "Output: " + output);
                    break;
                case 3:
                    // Execute function 3
                    output = channelProcessorUtils.executeFunction3(channelLines, paramLines);
                    redirectAttributes.addFlashAttribute("message",
                    "Output: " + output);
                    break;
                case 4:
                    // Execute function 4
                    output = channelProcessorUtils.executeFunction4(channelLines, paramLines);
                    redirectAttributes.addFlashAttribute("message",
                    "Output: " + output);
                    break;
                case 5:
                    // Calculate metric b
                    double b = channelProcessorUtils.calculateMetricB(channelLines, paramLines);
                    redirectAttributes.addFlashAttribute("message",
                    "Results for metric b: " + b);
                    break;
                default:
                    return "redirect:/";
            }

        } catch (IOException | MaxUploadSizeExceededException e) {
            e.printStackTrace();
        }
        // redirectAttributes.addFlashAttribute("message",
        //         "Results for metric b: " + outpuMetric.getB());
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