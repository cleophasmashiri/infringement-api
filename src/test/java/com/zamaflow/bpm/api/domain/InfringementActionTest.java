package com.zamaflow.bpm.api.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zamaflow.bpm.api.web.rest.TestUtil;

public class InfringementActionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InfringementAction.class);
        InfringementAction infringementAction1 = new InfringementAction();
        infringementAction1.setId(1L);
        InfringementAction infringementAction2 = new InfringementAction();
        infringementAction2.setId(infringementAction1.getId());
        assertThat(infringementAction1).isEqualTo(infringementAction2);
        infringementAction2.setId(2L);
        assertThat(infringementAction1).isNotEqualTo(infringementAction2);
        infringementAction1.setId(null);
        assertThat(infringementAction1).isNotEqualTo(infringementAction2);
    }
}
