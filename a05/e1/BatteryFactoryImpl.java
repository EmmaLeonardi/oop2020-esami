package a05.e1;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class BatteryFactoryImpl implements BatteryFactory {

	/**
	 * prima usa batteria (carica, se uso)->posso startare? come cala batteria
	 * (energia attuale, minuti accesa)->nuova energia prima smettere usare batteria
	 * (carica, se uso)->posso fermarla (?) come ricarica (capacità)-> nuova
	 * capacità
	 */
	private Battery fromParameters(BiPredicate<Double, Boolean> onStart, BiFunction<Double, Double, Double> dropEnergy,
			BiPredicate<Double, Boolean> onStop, Optional<Function<Double, Double>> recharge) {

		return new Battery() {

			private double charge = 1.00;
			private double capacity = 1.00;
			private boolean state = false;

			@Override
			public void stopUse(double duration) {
				if (onStop.test(charge, state)) {
					state = false;
					charge = dropEnergy.apply(charge, duration) < 0 ? 0 : dropEnergy.apply(charge, duration);
				} else {
					throw new IllegalStateException("Battery isn't in use");
				}

			}

			@Override
			public void startUse() {
				if (onStart.test(charge, state)) {
					state = true;
				} else {
					throw new IllegalStateException("Battery is empty or already in use");
				}
			}

			@Override
			public double getEnergy() {
				return charge;
			}

			@Override
			public void recharge() {
				if (recharge.isPresent()) {
					// Si può ricaricare
					charge = capacity;
					capacity = recharge.get().apply(capacity);
				}

			}

		};
	}

	@Override
	public Battery createSimpleBattery() {
		return createSimpleBatteryByDrop(1);

	}

	@Override
	public Battery createSimpleBatteryByDrop(double energyPerDurationDrop) {
		return fromParameters((a, b) -> true, (a, b) -> a - (b * energyPerDurationDrop), (a, b) -> true,
				Optional.empty());
	}

	@Override
	public Battery createSimpleBatteryWithExponentialDrop() {
		return fromParameters((a, b) -> true, (a, b) -> a / 2, (a, b) -> true, Optional.empty());
	}

	@Override
	public Battery createSecureBattery() {
		return fromParameters((a, b) -> Double.compare(a, 0) != 0 && b != true, (a, b) -> a - b, (a, b) -> b != false,
				Optional.empty());
	}

	@Override
	public Battery createRechargeableBattery() {
		return fromParameters((a, b) -> true, (a, b) -> a - b, (a, b) -> true, Optional.of(a -> a - 0.01));
	}

	@Override
	public Battery createSecureAndRechargeableBattery() {
		return fromParameters((a, b) -> Double.compare(a, 0) != 0 && b != true, (a, b) -> a - b, (a, b) -> b != false,
				Optional.of(a -> a - 0.01));
	}

}
