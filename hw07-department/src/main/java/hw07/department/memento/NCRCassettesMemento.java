package hw07.department.memento;

import hw07.department.adapter.NCRAdapter;
import hw07.department.atm.dispenser.Cassette;

import java.util.List;
import java.util.stream.Collectors;

public class NCRCassettesMemento implements Memento {
    private final List<Cassette> state;
    private final NCRAdapter originator;

    public NCRCassettesMemento(NCRAdapter originator, List<Cassette> state) {
        this.state = state.stream().map(Cassette::new).collect(Collectors.toList());
        this.originator = originator;
    }

    @Override
    public void restore() {
        originator.setState(state);
    }
}
