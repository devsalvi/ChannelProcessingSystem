package com.salvi.dev.cps;

import org.junit.jupiter.api.Test;
import com.salvi.dev.cps.service.ChannelProcessorUtils;
import com.salvi.dev.cps.service.Input;
import com.salvi.dev.cps.service.Parameter;

import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ChannelProcessorUtilsTest {
    ChannelProcessorUtils channelProcessorUtils = new ChannelProcessorUtils();

    @Test
    void testSetBaseInput() throws Exception {
        // Arrange
        String channelsFileStr = "channels.txt";
        List<Double> expectedX = Arrays.asList(0.814723686393179, 0.905791937075619, 0.126986816293506,
                0.913375856139019, 0.63235924622541, 0.0975404049994095, 0.278498218867048, 0.546881519204984,
                0.957506835434298, 0.964888535199277, 0.157613081677548, 0.970592781760616, 0.957166948242946,
                0.485375648722841, 0.8002804688888, 0.141886338627215, 0.421761282626275, 0.915735525189067,
                0.792207329559554, 0.959492426392903, 0.655740699156587, 0.0357116785741896, 0.849129305868777,
                0.933993247757551, 0.678735154857773, 0.757740130578333, 0.743132468124916, 0.392227019534168,
                0.655477890177557, 0.171186687811562, 0.706046088019609, 0.0318328463774207, 0.27692298496089,
                0.0461713906311539, 0.0971317812358475, 0.823457828327293, 0.694828622975817, 0.317099480060861,
                0.950222048838355, 0.0344460805029088, 0.438744359656398, 0.381558457093008, 0.765516788149002,
                0.795199901137063, 0.186872604554379, 0.489764395788231, 0.445586200710899, 0.646313010111265,
                0.709364830858073, 0.754686681982361, 0.276025076998578, 0.679702676853675, 0.655098003973841,
                0.162611735194631, 0.118997681558377, 0.498364051982143, 0.959743958516081, 0.340385726666133,
                0.585267750979777, 0.223811939491137, 0.751267059305653, 0.255095115459269, 0.505957051665142,
                0.699076722656686, 0.890903252535799, 0.959291425205444, 0.547215529963803, 0.138624442828679,
                0.149294005559057, 0.257508254123736, 0.840717255983663, 0.254282178971531, 0.814284826068816,
                0.243524968724989, 0.929263623187228, 0.349983765984809, 0.196595250431208, 0.251083857976031,
                0.616044676146639, 0.473288848902729, 0.351659507062997, 0.830828627896291, 0.585264091152724,
                0.54972360829114, 0.91719366382981, 0.285839018820374, 0.757200229110721, 0.753729094278495,
                0.380445846975357, 0.567821640725221, 0.0758542895630636, 0.0539501186666072, 0.530797553008973,
                0.779167230102011, 0.934010684229183, 0.12990620847373, 0.568823660872193, 0.469390641058206,
                0.0119020695012414, 0.337122644398882);
        Input expectedInput = new Input();
        expectedInput.getChannel().setX(expectedX);

        // Act
        Input actualInput = channelProcessorUtils.setBaseInput(channelsFileStr);

        // Assert
        assertEquals(expectedInput.getChannel().getX(), actualInput.getChannel().getX());
    }

    @Test
    void testSetBaseInput_invalidChannelFile() {
        // Arrange
        String channelsFileStr = "channelsX.txt";

        // Act & Assert

        assertThrows(NoSuchFileException.class,
                () -> channelProcessorUtils.setBaseInput(channelsFileStr));
    }

    @Test
    void testSetBaseParameter() throws Exception {
        // Arrange
        String parametersFileStr = "parameters.txt";
        Parameter expectedParameter = new Parameter();
        expectedParameter.setM(2.0);
        expectedParameter.setC(0.5);

        // Act
        Parameter actualParameter = channelProcessorUtils.setBaseParameter(parametersFileStr);

        // Assert
        assertEquals(expectedParameter.getM(), actualParameter.getM());
        assertEquals(expectedParameter.getC(), actualParameter.getC());
    }

    @Test
    void testSetBaseParameter_invalidParametersFile() throws Exception {
        // Arrange
        String parametersFileStr = "parametersX.txt";

        assertThrows(NoSuchFileException.class,
                () -> channelProcessorUtils.setBaseParameter(parametersFileStr));
    }
}